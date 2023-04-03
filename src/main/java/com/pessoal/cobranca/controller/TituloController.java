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

import com.pessoal.cobranca.model.StatusTitulo;
import com.pessoal.cobranca.model.Titulo;
import com.pessoal.cobranca.repository.TituloRepository;
import com.pessoal.cobranca.repository.filter.TituloFilter;
import com.pessoal.cobranca.service.CadastroTituloService;


@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	private static final String CADASTROVIEW = "CadastroTitulo";
	
	@Autowired
	private TituloRepository tituloRepository;
	
	@Autowired
	private CadastroTituloService cadastroTituloService;
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTROVIEW);
		mv.addObject(new Titulo());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		
		if(errors.hasErrors()) {
			return CADASTROVIEW;	
		}
		
		try {
			
			cadastroTituloService.salvar(titulo);
			attributes.addFlashAttribute("mensagem", "Tiulo Salvo com Sucesso!");
			return "redirect:/titulos/novo";
			
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTROVIEW;
		}
		
	}
	
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute(name = "filtro") TituloFilter filtro) {
		
		List<Titulo> todosTitulos = cadastroTituloService.filtrar(filtro);
		 ModelAndView mv = new ModelAndView("PesquisaTitulos");
		 mv.addObject("titulos", todosTitulos);
		return mv;
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		
		@SuppressWarnings("deprecation")
		Titulo titulo = tituloRepository.getOne(codigo); 
		
		ModelAndView mv = new ModelAndView(CADASTROVIEW);
		mv.addObject(titulo);
		
		return mv;
		
	}
	
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		
		cadastroTituloService.excluir(codigo);
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
		return "redirect:/titulos";
	}
	
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		return cadastroTituloService.receber(codigo);
		 
	}
	
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStattus(){
		return Arrays.asList(StatusTitulo.values());
	}

}
