package com.busanit.controller;

import com.busanit.domain.BoardDTO;
import com.busanit.entity.Board;
import com.busanit.entity.BoardAttach;
import com.busanit.entity.Reply;
import com.busanit.repository.BoardRepository;
import com.busanit.service.BoardService;
import com.busanit.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {

    private BoardRepository BR;
    private ReplyService replyService;
    private BoardService boardService;

    /*@GetMapping("/list")
    public String listForm(Model model){
        List<Board> boardList = BR.findAll();



        model.addAttribute("boardlist", boardList);
        return "board/list";
    }*/

    /*@GetMapping("/list")
    public String listForm(Model model, @PageableDefault(size = 5, sort = "bno",direction = Sort.Direction.DESC)Pageable pageable){

        Page<Board> result = boardService.getBoardList(pageable);
        *//*int blockLimit = 3;
        int startPage = (((int) Math.ceil(((double)pageable.getPageNumber()/blockLimit)))-1) * blockLimit +1;
        int endPage = Math.min((startPage + blockLimit - 1), result.getTotalPages());*//*
        model.addAttribute("list", result );
        *//*model.addAttribute("startpage", startPage);
        model.addAttribute("endpage", endPage);*//*
        return "board/list";
    }*/
    @GetMapping("/list")
    public String listForm(Model model, @RequestParam(defaultValue = "") String searchType, @RequestParam(defaultValue = "") String keyword, @PageableDefault(size = 5, sort = "bno",direction = Sort.Direction.DESC)Pageable pageable){

        if(searchType.equals("title")){

            Page<Board> result = boardService.getBoardList(keyword, pageable);

            model.addAttribute("list", result );

        } else if (searchType.equals("content")) {
            Page<Board> result = boardService.getBoardList2(keyword, pageable);
            model.addAttribute("list", result );


        }else{
            Page<Board> result =  boardService.getBoardList0(pageable);
            model.addAttribute("list",result);
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("searchType", searchType);


        return "board/list";
    }

    @GetMapping("/view")
    public String viewForm(String bno, Model model){
        Board board = BR.findByBno(Long.parseLong(bno));
        model.addAttribute("board", board);
//        String[] list = board.getFileName().split(";");

        return "board/view";
    }

    @GetMapping("/register")
    public String registerForm(){
        return "board/register";
    }

    @PostMapping("/register")
    public String register(BoardDTO boardDto){

        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setWriter(boardDto.getWriter());

        board.setAttachList(boardDto.getUploadFiles());
        /*BR.save(board);*/

        List<BoardAttach> attachList = new ArrayList<>();
        // 파라미터로 받은 파일명 list를 다시 attachList에 담음
        for(BoardAttach attach : board.getAttachList()){
            log.info("board : "+board);
            log.info("fileName : " + attach.getFileName());
            attach.setBoard(board);
            attach.setFileName(attach.getFileName());
            attachList.add(attach);
        }
        return "redirect:/board/list";
    }

    @GetMapping("/update")
    public String updateForm(Long bno, Model model){
        Board board = BR.findByBno(bno);

        model.addAttribute("board", board);
        return "board/update";
    }

    @PostMapping("/update")
    public String update(BoardDTO boardDto){
        Board board = new Board();
        board.setBno(boardDto.getBno());
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setWriter(boardDto.getWriter());
//        board.setRegDate(boardDto.getRegDate());
//        board.setUpdateDate(LocalDateTime.now());

        BR.save(board);


        return "redirect:/board/view?bno=" + board.getBno();
    }

    @GetMapping("/delete")
    public String deleteForm(Long bno, Model model){
        Board board = BR.findByBno(bno);

        model.addAttribute("board", board);
        return "board/delete";
    }

    @PostMapping("/delete")
    public String delete(Long bno, HttpServletResponse response){
        BR.deleteById(bno);
        /*try {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('삭제 성공')</script>");
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally{

        }*/
        return "redirect:/board/list";
    }



}
