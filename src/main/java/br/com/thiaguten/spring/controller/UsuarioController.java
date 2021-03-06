/*
 * #%L
 * %%
 * Copyright (C) 2015 - 2016 Thiago Gutenberg Carvalho da Costa.
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Thiago Gutenberg Carvalho da Costa. nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package br.com.thiaguten.spring.controller;

import br.com.thiaguten.spring.model.Usuario;
import br.com.thiaguten.spring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController {

    private static final String LISTAR_PAGE = "/usuario/listarUsuario";
    private static final String MANTER_PAGE = "/usuario/manterUsuario";
    private static final String USUARIO_KEY = "usuario";
    private static final String USUARIOS_KEY = "usuarios";

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo() {
        return new ModelAndView(MANTER_PAGE, USUARIO_KEY, new Usuario());
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute(USUARIO_KEY, new Usuario());
        model.addAttribute(USUARIOS_KEY, usuarioService.listar());
        return LISTAR_PAGE;
    }

    @RequestMapping(value = "/remover/{id}", method = RequestMethod.GET)
    public String remover(@PathVariable("id") long id) {
        this.usuarioService.deletar(id);
        return "redirect:/usuario/listar";
    }

    @RequestMapping(value = "/alterar/{id}", method = RequestMethod.GET)
    public ModelAndView alterar(@PathVariable("id") long id) {
        return new ModelAndView(MANTER_PAGE, USUARIO_KEY, usuarioService.recuperar(id));
    }

    @RequestMapping(value = "/manter", method = {RequestMethod.GET, RequestMethod.POST})
    public String manter(@Valid @ModelAttribute(USUARIO_KEY) Usuario usuario,
                         BindingResult result, SessionStatus status, Model model) {

        if (!result.hasErrors()) {
            boolean sucesso = this.usuarioService.salvarOuAtualizar(usuario);
            if (sucesso) {
                status.setComplete();
                return "redirect:/usuario/listar";
            } else {
                setMensagemNegocial(model, "Usuário já cadastrado");
            }
        }
        return MANTER_PAGE;
    }

    @RequestMapping(value = "/pesquisar", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView pesquisar(@ModelAttribute(USUARIO_KEY) Usuario usuario, Model model) {
        List<Usuario> usuarios = usuarioService.pesquisar(usuario);
        if (usuarios == null || usuarios.isEmpty()) {
            setMensagemNegocial(model, "Nenhum usuário encontrado");
        }
        return new ModelAndView(LISTAR_PAGE, USUARIOS_KEY, usuarios);
    }

    private void setMensagemNegocial(Model model, String mensagem) {
        model.addAttribute("usuario_mensagem_negocial", mensagem);
    }
}
