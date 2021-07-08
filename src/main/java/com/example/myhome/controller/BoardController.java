package com.example.myhome.controller;

import com.example.myhome.model.Board;
import com.example.myhome.repository.BoardRepository;
import com.example.myhome.service.BoardService;
import com.example.myhome.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardrepo;

    @Autowired
     private BoardValidator boardValidator;

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public String list(Model model,
                       @PageableDefault(size = 1) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
        //Page<Board> boards =boardrepo.findAll(pageable);
        Page<Board> boards =boardrepo.findByTitleOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() -4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber()+4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("board",boards);

        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if (id ==null) {
            model.addAttribute("board", new Board());
        } else {
            Board board = boardrepo.findById(id).orElse(null);
            model.addAttribute("board", board);
        }


        return "board/form";
    }

    @PostMapping("/form")
    public String postForm(@Valid Board board, BindingResult bindingResult, Authentication authentication) {
        //validation 업무
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/form";
        }


        // Authentication a = SecurityContextHolder.getContext().getAuthentication(); //같은 방법
        String username = authentication.getName();

        boardService.save(username, board);

        boardrepo.save(board);
        return "redirect:/board/list";
    }
}
