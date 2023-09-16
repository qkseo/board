package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;

import java.io.File;

import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    //글작성
    public void write(Board board, MultipartFile file) throws Exception{
        //저장할 경로 생성
        // 파일 저장 경로 생성
        String projectPath= System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";

        // 파일 이름 생성
        UUID uuid=UUID.randomUUID();
        String fileName=uuid+"_"+file.getOriginalFilename();

        // 파일을 디스크에 저장
        File saveFile = new File(projectPath,fileName);
        file.transferTo(saveFile);

        // 엔티티에 파일 정보 설정
        board.setFilename(fileName);
        board.setFilepath("/files/"+fileName);

        // 엔티티를 저장
        boardRepository.save(board);
    }

    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){
        // 페이징 및 정렬을 적용하여 게시글 리스트를 반환
        return boardRepository.findAll(pageable);
    }

    //특정게시글 불러오기
    public Board boardView(Integer id){
        // 특정 게시글을 ID를 기반으로 조회
        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    public void boardDeleate(Integer id){
        // 특정 게시글을 ID를 기반으로 삭제
        boardRepository.deleteById(id);
    }
}
