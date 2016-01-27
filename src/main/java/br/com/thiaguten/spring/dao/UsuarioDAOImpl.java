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
package br.com.thiaguten.spring.dao;

import br.com.thiaguten.persistence.dao.GenericBaseDAO;
import br.com.thiaguten.persistence.spi.PersistenceProvider;
import br.com.thiaguten.spring.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("usuarioDAO")
public class UsuarioDAOImpl extends GenericBaseDAO<Usuario, Long> implements UsuarioDAO {

    private PersistenceProvider persistenceProvider;

    @Override
    public PersistenceProvider getPersistenceProvider() {
        return persistenceProvider;
    }

    @Autowired
    @Qualifier("jpaPersistenceProvider")
    public void setPersistenceProvider(PersistenceProvider persistenceProvider) {
        this.persistenceProvider = persistenceProvider;
    }

    @Override
    public List<Usuario> pesquisar(Usuario u) {
        Map<String, Object> params = null;
        StringBuilder jpql = new StringBuilder("SELECT u FROM Usuario u ");
        if (u != null) {

            jpql.append("WHERE 1=1 ");
            params = new HashMap<>();

            if (StringUtils.hasText(u.getNome())) {
                jpql.append("AND UPPER(u.nome) LIKE :nome ");
                params.put("nome", "%" + u.getNome().toUpperCase() + "%");

            } else if (StringUtils.hasLength(u.getEmail())) {
                jpql.append("AND UPPER(u.email) LIKE :email ");
                params.put("email", "%" + u.getEmail().toUpperCase() + "%");

            } else if (u.getIdade() != null) {
                jpql.append("AND u.idade = :idade ");
                params.put("idade", u.getIdade());
            }

        }
        return getPersistenceProvider().findByQueryAndNamedParams(getEntityClass(), jpql.toString(), params);
    }

    @Override
    public Long contarPorEmail(Usuario u) {
        Map<String, Object> params = null;
        StringBuilder jpql = new StringBuilder("SELECT COUNT(u) FROM Usuario u ");
        if (u != null) {

            jpql.append("WHERE 1=1 ");
            params = new HashMap<>();

            if (u.hasID()) {
                jpql.append("AND u.id != :id ");
                params.put("id", u.getId());
            }
            if (StringUtils.hasText(u.getEmail())) {
                jpql.append("AND UPPER(u.email) LIKE :email");
                params.put("email", u.getEmail().toUpperCase());
            }
        }
        return getPersistenceProvider().countByQueryAndNamedParams(Long.class, jpql.toString(), params);
    }

}
