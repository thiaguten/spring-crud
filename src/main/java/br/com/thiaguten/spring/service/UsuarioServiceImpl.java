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
package br.com.thiaguten.spring.service;

import br.com.thiaguten.spring.dao.UsuarioDAO;
import br.com.thiaguten.spring.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;

    @Autowired
    public UsuarioServiceImpl(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public boolean emailJaExiste(Usuario usuario) {
        return (usuarioDAO.contarPorEmail(usuario) > 0);
    }

    @Override
    public boolean salvarOuAtualizar(Usuario usuario) {
        if (!emailJaExiste(usuario)) {
            if (usuario.hasID()) {
                usuarioDAO.update(usuario);
            } else {
                usuarioDAO.save(usuario);
            }
            return true;
        }
        return false;
    }

    @Override
    public void deletar(Long id) {
        if (id != null) {
            usuarioDAO.deleteById(id);
        }
    }

    @Override
    public List<Usuario> pesquisar(Usuario usuario) {
        List<Usuario> usuarios = usuarioDAO.pesquisar(usuario);
        if (usuarios == null) {
            return Collections.emptyList();
        }
        return usuarios;
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = usuarioDAO.findByNamedQuery(Usuario.LISTAR_TODOS_NQ);
//        List<Usuario> usuarios = usuarioDAO.findAll();
        if (usuarios == null) {
            return Collections.emptyList();
        }
        return usuarios;
    }

    @Override
    public Usuario recuperar(Long id) {
        Usuario usuario = null;
        if (id != null) {
            usuario = usuarioDAO.findById(id);
        }
        return usuario;
    }

}
