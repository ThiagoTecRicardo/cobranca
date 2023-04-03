package com.pessoal.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pessoal.cobranca.model.Receita;
import com.pessoal.cobranca.model.StatusReceita;
import com.pessoal.cobranca.repository.ReceitaRepository;
import com.pessoal.cobranca.repository.filter.ReceitaFilter;
import com.pessoal.cobranca.service.CadastroReceitaervice;


@Controller
@RequestMapping("/receitas")
public class ReceitaController {
	
	private static final String CADASTROVIEW = "CadastroReceita";
	
	@Autowired
	private ReceitaRepository receitaRepository;
	
	@Autowired
	private CadastroReceitaervice cadastroReceitaervice;
	
	@RequestMapping("/nova")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTROVIEW);
		mv.addObject(new Receita());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Receita receita, Errors errors, RedirectAttributes attributes) {
		
		if(errors.hasErrors()) {
			return CADASTROVIEW;
		}
		
		try {
			cadastroReceitaervice.salvar(receita);
			
			attributes.addFlashAttribute("mensagem", "Receita Salva com Sucesso!");
			
			return "redirect:/receitas/nova";
			
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTROVIEW;
		}
		
		
	}
	
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute(name = "filtro") ReceitaFilter filtro) {
		
		List<Receita> todasReceitas = cadastroReceitaervice.filtrar(filtro);
		 ModelAndView mv = new ModelAndView("PesquisaReceitas");
		 mv.addObject("receitas", todasReceitas);
		return mv;
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		
		@SuppressWarnings("deprecation")
		Receita receita = receitaRepository.getOne(codigo); 
		
		ModelAndView mv = new ModelAndView(CADASTROVIEW);
		mv.addObject(receita);
		
		return mv;
		
	}
	
	
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		cadastroReceitaervice.excluir(codigo);
		
		attributes.addFlashAttribute("mensagem", "Receita excluída com sucesso!");
		return "redirect:/receitas";
	}
	
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		return cadastroReceitaervice.receber(codigo);
		 
	}
	
	
	@ModelAttribute("todosStatusReceita")
	public List<StatusReceita> todosStattus(){
		return Arrays.asList(StatusReceita.values());
	}

}
