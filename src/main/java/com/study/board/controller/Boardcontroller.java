package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;


@Controller
public class Boardcontroller {

    @Autowired
    private BoardService boardService;

    // 게시글 작성 페이지로 이동하는 메서드
   @GetMapping("/board/write")//localhost:8090/board/write
    public String boardWriteForm(){

        return "boardwrite";
    }
    // 게시글 작성 처리를 수행하는 메서드
    @PostMapping("/board/writedopro")
    public String boardWritedopro(Board board, Model model, MultipartFile file)throws  Exception{
        // boardService.write 메서드를 호출하여 게시글 작성 및 파일 업로드를 처리합니다.
        boardService.write(board,file);

        // 작업 성공 메시지 및 이동할 URL을 모델에 추가하여 message.html로 이동합니다.
        model.addAttribute("message","글작성이 완료되었습니다");
        model.addAttribute("searchUrl","/board/list");
       return "message";
    }
    // 게시글 목록 페이지로 이동하는 메서드
    @GetMapping("/board/list")
    public String boardlist(Model model, @PageableDefault(page=0,size=10,sort="id",direction = Sort.Direction.DESC)Pageable pageable){


       model.addAttribute("list",boardService.boardList(pageable));
       return "boardlist";
    }
    // 게시글 상세 내용을 조회하는 메서드
    @GetMapping("/board/view")//localhost:8090/board/view?id=1
    public String boardView(Model model, Integer id){


       model.addAttribute("board",boardService.boardView(id));
       return "boardview";
    }
    // 게시글 삭제를 처리하는 메서드
    @GetMapping("/board/delete")
    public String boardDelete(Integer id){

       boardService.boardDeleate(id);
       return "redirect:/board/list";
    }
    // 게시글 수정 페이지로 이동하는 메서드
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){

        model.addAttribute("board",boardService.boardView(id));

       return "boardmodify";
    }
    // 게시글 수정 처리를 수행하는 메서드
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, MultipartFile file) throws Exception{
        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardService.write(boardTemp, file); // Save the updated board
        return "redirect:/board/list";
    }

}
