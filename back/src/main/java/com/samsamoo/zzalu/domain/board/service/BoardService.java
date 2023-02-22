package com.samsamoo.zzalu.domain.board.service;

import com.samsamoo.zzalu.global.advice.NotFoundException;
import com.samsamoo.zzalu.global.auth.sevice.JwtTokenProvider;
import com.samsamoo.zzalu.domain.board.dto.*;
import com.samsamoo.zzalu.domain.board.entity.Board;
import com.samsamoo.zzalu.domain.board.repo.BoardRepository;
import com.samsamoo.zzalu.domain.gifs.entity.Gifs;
import com.samsamoo.zzalu.domain.gifs.repository.GifsRepository;
import com.samsamoo.zzalu.domain.member.entity.Member;
import com.samsamoo.zzalu.domain.member.exception.NotMatchException;
import com.samsamoo.zzalu.domain.member.repository.MemberRepository;
import com.samsamoo.zzalu.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final GifsRepository gifsRepository;


    public CreateBoardResposne createBoard(String token, String requestName) {
        // 토큰 검증 > spring config로 빼기
        memberService.checkToken(token);

        // 토큰에서 맴버 꺼내서 아이디까지 꺼내기
        Member requestMember = jwtTokenProvider.getMember(token);

        // 보드 이름 중복인지 검증
        List<Board> myBoards = requestMember.getBoards();
        for (Board board : myBoards) {

            if (board.getBoardName().equals(requestName)) {
                throw new NotMatchException("보드 이름이 중복입니다.");
            }
        }

        // 보드를 생성
        Board board = Board.builder()
                .boardName(requestName)
                .member(requestMember)
                .build();

        // 맴버에도 보드 저장
        requestMember.createBoard(board);

        // 보드를 저장
        boardRepository.save(board);
        memberRepository.save(requestMember);
        return new CreateBoardResposne(board);
    }

    public GifsBoardList getGifsBoard(String token, Long gifId) {
        // 토큰 검증
        memberService.checkToken(token);
        // user 반환
        Member requestMember = jwtTokenProvider.getMember(token);

        // user의 보드 불러오기
        List<Board> boards = requestMember.getBoards();

        // new list 만들기
        List<GifsBoardInfo> gifsBoardInfos = new ArrayList<>();

        Gifs gif = gifsRepository.findById(gifId)
                .orElseThrow(() -> new NotFoundException("짤이 존재하지 않습니다.")); //>>>>>

        // for문 돌면서 gif 포함 여부와 id를 dto에 넣기
        for(Board board : boards) {
            Boolean gifContainState = board.getGifs().contains(gif);
            GifsBoardInfo gifsBoardInfo = new GifsBoardInfo(board.getId(), board.getBoardName(), gifContainState);
            gifsBoardInfos.add(gifsBoardInfo);
        }
        // return dto (list)
        return new GifsBoardList(gifsBoardInfos);

    }

    public Board getBoardById(Long boardId) {
        Board board = boardRepository.findBoardById(boardId)
                .orElseThrow(() -> new NotFoundException("보드를 찾을 수 없습니다."));
        return board;
    }
    public Gifs getGifById(Long gifId) {
        Gifs gif = gifsRepository.findById(gifId)
                .orElseThrow(() -> new NotFoundException(gifId.toString() +"번 gif를 찾을 수 없습니다."));
        return gif;
    }
    public void checkGifListEmpty(List<Long> gifList) {
        if (gifList.size() == 0) {
            throw new NotMatchException("gif 리스트가 비었습니다.");  //>>>>>
        }
    }


    public GifList getGifs(Long boardId) {
        // 보드에 포함된 애들 return
        Board board = getBoardById(boardId);

        List<GifInfo> gifInfos = new ArrayList<>();
        for(Gifs gif: board.getGifs()) {
            gifInfos.add(new GifInfo(gif.getId(), gif.getGifPath()));
        }
        return new GifList(gifInfos);
    }

    public void updateBoard(String token, Long gifId, GifsBoardList gifsBoardList) {
        Member member = jwtTokenProvider.getMember(token);

        List<GifsBoardInfo> boardInfoList = gifsBoardList.getBoards();
        Gifs gif = getGifById(gifId);

        for (GifsBoardInfo boardInfo : boardInfoList) {
            Board repoBoard = getBoardById(boardInfo.getId());

            // 요청의 gif state와 저장된 gif state가 다르면 상태를 저장 or 삭제
            Boolean requestState = boardInfo.getGifContainState();
            if (requestState != repoBoard.getGifs().contains(gif)) {
                if (requestState == true) {
                    insertGif(repoBoard, gif, member);
                } else {
                    deleteGif(repoBoard, gif);
                }
                gifsRepository.save(gif);
            }
        }
        // return
    }

    private void deleteGif(Board board, Gifs gif) {
        board.getGifs().remove(gif);
        boardRepository.save(board);
//        gif.decreaseScrap();
//        gifsRepository.save(gif);
    }

    private void insertGif(Board board, Gifs gif, Member member) {
        // member 회원 통계 로직 추가
        board.getGifs().add(0, gif);
        boardRepository.save(board);
//        gif.increaseScrap();
//        gifsRepository.save(gif);
    }

    public void deleteGifFromBoard(Long boardId, List<Long> gifList) {
        checkGifListEmpty(gifList);

        Board board = getBoardById(boardId);

        for (Long gifId : gifList) {
            Gifs gif = getGifById(gifId);

            // real로 보드에 짤이 담겨있는지 확인
            if (!board.getGifs().contains(gif)) {
                throw new NotMatchException(gif.getId().toString() + "번 gif는 board에 담겨있지 않습니다.");
            }
            deleteGif(board, gif);
        }
    }

    public void insertGifFromBoard(String token, Long boardId, List<Long> gifIdList) {
        Member member = jwtTokenProvider.getMember(token);

        // 매서드로 분리
        checkGifListEmpty(gifIdList);
        // 매서드로 분리
        Board board = getBoardById(boardId);

        for (Long gifId : gifIdList) {
            Gifs gif = getGifById(gifId);

            // 이미 담겨있으면 삭제 후 저장
            if (board.getGifs().contains(gif)) {
                deleteGif(board, gif);
            }
            insertGif(board,gif, member);

        }
    }

    public void updateBoardName(String token, Long boardId, String newBoardName) {
        // 토큰 검증
        memberService.checkToken(token);
        Board board = getBoardById(boardId);
        // 본인 보드인지 확인 and
        // 보드 이름 중복인지 검증
        Member member = jwtTokenProvider.getMember(token);
        List<Board> myBoards = member.getBoards();
        if (!myBoards.contains(board)) {
            throw new NotMatchException("수정하려는 보드는 회원님의 보드가 아닙니다.");
        }
        for (Board existBoard : myBoards) {
            if (existBoard.getBoardName().equals(newBoardName)) {
                throw new NotMatchException("보드 이름이 중복입니다.");
            }
        }
        board.updateBoardName(newBoardName);
        boardRepository.save(board);
    }

    public void deleteBoard(String token, Long boardId) {
        Board board = getBoardById(boardId);  //>>>>>
        Member member = jwtTokenProvider.getMember(token);
        List<Board> myBoards = member.getBoards();

        // 본인 보드인지 확인 and
        if (!myBoards.contains(board)) {
            throw new NotMatchException("삭제하려는 보드는 회원님의 보드가 아닙니다.");
        } else {
            boardRepository.delete(board);
        }

    }


}
