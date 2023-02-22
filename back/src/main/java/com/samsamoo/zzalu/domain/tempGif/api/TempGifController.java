package com.samsamoo.zzalu.domain.tempGif.api;

import com.samsamoo.zzalu.domain.tempGif.dto.TempGifRequest;
import com.samsamoo.zzalu.domain.tempGif.dto.TempGifResponse;
import com.samsamoo.zzalu.domain.tempGif.entity.TempGif;
import com.samsamoo.zzalu.domain.tempGif.service.TempGifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/temp")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TempGifController {
    private final TempGifService tempGifService;

    //--------------------------------모든 임시 게시물 조회---------------------------------
    @GetMapping("/all")
    public ResponseEntity getAllTempGif(@RequestHeader(value = "Authorization") String bearerToken) {
        // 시큐리티에서 체크
        String token = bearerToken.substring(7);
        List<TempGifResponse> tempGifList = tempGifService.getAllTempGif(token);
        return new ResponseEntity<>(Map.of("tempGifList", tempGifList),HttpStatus.OK);
    }
    //--------------------------------임시 게시물 생성 요청---------------------------------
    @PostMapping
    public ResponseEntity createGifReq(@RequestHeader(value = "Authorization") String bearerToken, @ModelAttribute TempGifRequest request) throws IOException {
        String token = bearerToken.substring(7);
        Long savedTempGifId = tempGifService.createGifReq(token, request);
        return new ResponseEntity<>(Map.of("tempGifId", savedTempGifId),HttpStatus.CREATED);
    }
    //--------------------------------임시 게시물 수정 요청+1-------------------------------
    @PutMapping("/{tempId}")
    public ResponseEntity increaseCount(@RequestHeader(value = "Authorization") String bearerToken, @PathVariable Long tempId) {
        String token = bearerToken.substring(7);
        tempGifService.increaseCount(token, tempId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //---------------------------------임시 게시물 삭제 요청--------------------------------
    @DeleteMapping ("/{tempId}")
    public ResponseEntity deleteTempGif(@PathVariable Long tempId) {
        tempGifService.deleteTempGif(tempId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
