package com.samsamoo.zzalu.domain.board.api;

import com.samsamoo.zzalu.domain.board.dto.*;
import com.samsamoo.zzalu.domain.board.service.BoardService;
import com.samsamoo.zzalu.domain.member.exception.NotMatchException;
import com.samsamoo.zzalu.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;

    //--------------------------------------보드 생성-----------------------------------------
    @PostMapping
    public ResponseEntity<CreateBoardResposne> createBoard(@RequestHeader(value = "Authorization")String bearerToken, @RequestBody Map<String, String> request) {
        String token = bearerToken.substring(7);
        if (request.get("boardName").isBlank()) {
            throw new NotMatchException("보드 이름을 입력해주세요.");
        }
        CreateBoardResposne response = boardService.createBoard(token, request.get("boardName"));
        return ResponseEntity.created(URI.create("/boards/" + response.getId())).body(response);
    }

    //-----------------------------------특정 짤의 보드 정보 불러오기--------------------------------
    @GetMapping(params = "gifId")
    public ResponseEntity<GifsBoardList> getGifsBoard(@RequestHeader(value = "Authorization")String bearerToken, @RequestParam Long gifId) {
        String token = bearerToken.substring(7);
        GifsBoardList gifsBoardList = boardService.getGifsBoard(token, gifId);
        return ResponseEntity.ok(gifsBoardList);
    }
    //-----------------------------------사용자의 보드 불러오기-------------------------------------
    @GetMapping(params = "username")
    public ResponseEntity<MembersBoardList> getMembersBoard(@RequestParam String username) {
        MembersBoardList membersBoardList = memberService.getMembersBoard(username);
        return ResponseEntity.ok(membersBoardList);
    }
    //--------------------------------보드 상세 불러오기(짤들)--------------------------------------
    @GetMapping("/{boardId}")
    public ResponseEntity<GifList> getGifs(@PathVariable Long boardId) {
        GifList gifList = boardService.getGifs(boardId);
        return ResponseEntity.ok(gifList);
    }

    //-----------------------------------짤을 보드에 추가 및 삭제(체크박스로)--------------------------------
    @PutMapping("/gif/{gifId}")
    public ResponseEntity updateBoard(@RequestHeader(value = "Authorization")String bearerToken, @PathVariable Long gifId, @RequestBody GifsBoardList gifsBoardList) {
        String token = bearerToken.substring(7);
        boardService.updateBoard(token, gifId, gifsBoardList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //---------------------------------보드에서 짤 삭제-------------------------------------
    @DeleteMapping("/gif/{boardId}")
    public ResponseEntity deleteGifFromBoard(@PathVariable Long boardId, @RequestBody GifIdList request) {
        // 토큰은 SecuityConfig에서 처리
        boardService.deleteGifFromBoard(boardId, request.getGifIdList());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //---------------------------------보드에서 짤 삽입-------------------------------------
    @PostMapping ("/{boardId}")
    public ResponseEntity insertGifFromBoard(@RequestHeader(value = "Authorization")String bearerToken, @PathVariable Long boardId, @RequestBody GifIdList request) {
        // 토큰은 SecuityConfig에서 처리
        String token = bearerToken.substring(7);
        boardService.insertGifFromBoard(token, boardId, request.getGifIdList());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //---------------------------------보드 이름 수정-------------------------------------
    @PutMapping ("/{boardId}")
    public ResponseEntity updateBoardName(@RequestHeader(value = "Authorization")String bearerToken, @PathVariable Long boardId, @RequestBody Map<String, String> request) {
        // 토큰은 SecuityConfig에서 처리
        String token = bearerToken.substring(7);
        boardService.updateBoardName(token, boardId, request.get("newBoardName"));
//        return ResponseEntity.ok(Map.of("result", "ok"));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //---------------------------------보드 삭제-------------------------------------
    @DeleteMapping ("/{boardId}")
    public ResponseEntity deleteBoard(@RequestHeader(value = "Authorization")String bearerToken, @PathVariable Long boardId) {
        // 토큰은 SecuityConfig에서 처리
        String token = bearerToken.substring(7);
        boardService.deleteBoard(token, boardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
