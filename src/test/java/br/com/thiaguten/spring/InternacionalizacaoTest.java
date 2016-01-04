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
package br.com.thiaguten.spring;

import br.com.thiaguten.spring.util.ResourceUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class InternacionalizacaoTest {

    private static final Logger log = LoggerFactory.getLogger(InternacionalizacaoTest.class);

    private static ApplicationContext context;

    @BeforeClass
    public static void init() {
        log.info("Inicializando o contexto do Spring");
        context = new ClassPathXmlApplicationContext("spring/root-applicationContext.xml");
        Assert.assertNotNull(context);
    }

    @Test
    public void obterMensagemDeDiferenteLocales() {
        Locale US = Locale.US;
        Locale BRASIL = new Locale("pt", "BR");

        final String chave = "label.listar.usuario";
        Assert.assertEquals("List User", context.getMessage(chave, null, US));
        Assert.assertEquals("Listar Usuário", context.getMessage(chave, null, BRASIL));

        resourceBundleTest("i18n/messages", US);
        resourceBundleTest("i18n/messages_pt_BR", BRASIL);
    }

    private void resourceBundleTest(String basename, Locale locale) {
        ResourceBundle bundle = ResourceUtils.getResource(basename);
        Properties properties = ResourceUtils.convertResourceBundleToProperties(bundle);
        for (Map.Entry<Object, Object> entrySet : properties.entrySet()) {
            Object key = entrySet.getKey();
            Object value = entrySet.getValue();

            String mensagem = context.getMessage(key.toString(), null, locale);
            log.info(locale.toString() + " - " + mensagem);
            Assert.assertEquals(value, mensagem);
        }
    }
}
