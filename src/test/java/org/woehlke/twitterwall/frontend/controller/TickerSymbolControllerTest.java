package org.woehlke.twitterwall.frontend.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.frontend.controller.common.PrepareDataTest;
import org.woehlke.twitterwall.oodm.model.TickerSymbol;
import org.woehlke.twitterwall.oodm.service.TickerSymbolService;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.woehlke.twitterwall.frontend.content.ContentFactory.FIRST_PAGE_NUMBER;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TickerSymbolControllerTest {

    private static final Logger log = LoggerFactory.getLogger(TickerSymbolControllerTest.class);

    @Autowired
    private TickerSymbolController controller;

    @Autowired
    private TickerSymbolService tickerSymbolService;

    @Autowired
    private PrepareDataTest prepareDataTest;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void controllerIsPresentTest(){
        log.info("controllerIsPresentTest");
        assertThat(controller).isNotNull();
    }

    @Test
    public void setupTestData() throws Exception {
        String msg = "setupTestData: ";
        prepareDataTest.getTestDataTweets(msg);
        prepareDataTest.getTestDataUser(msg);
        Assert.assertTrue(true);
    }

    @Commit
    @WithMockUser
    @Test
    public void getAllTest() throws Exception {
        String msg = "getAllTest: ";

        MvcResult result = this.mockMvc.perform(get("/tickersymbol/all"))
                .andExpect(status().isOk())
                .andExpect(view().name( "tickersymbol/all"))
                .andExpect(model().attributeExists("myPageContent"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        log.info(msg+content);
        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @Commit
    @WithMockUser
    @Test
    public void getTickerSymbolById() throws Exception {
        String msg ="getTickerSymbolById: ";
        TickerSymbol oneTickerSymbol = findOneTickerSymbol();
        if(oneTickerSymbol != null) {
            long id = oneTickerSymbol.getId();
            MvcResult result = this.mockMvc.perform(get("/tickersymbol/" + id))
                    .andExpect(status().isOk())
                    .andExpect(view().name("tickersymbol/id"))
                    .andExpect(model().attributeExists("users"))
                    .andExpect(model().attributeExists("latestTweets"))
                    .andExpect(model().attributeExists("tickersymbol"))
                    .andExpect(model().attributeExists("page"))
                    .andReturn();

            String content = result.getResponse().getContentAsString();

            log.info(msg + "#######################################");
            log.info(msg + "#######################################");
            log.info(msg + content);
            log.info(msg + "#######################################");
            log.info(msg + "#######################################");
            Assert.assertTrue(true);
        }
    }

    private TickerSymbol findOneTickerSymbol() {
        int size = 1;
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, size);
        Page<TickerSymbol> tickerSymbolPage = tickerSymbolService.getAll(pageRequest);
        if(tickerSymbolPage.getContent().size()>0){
            return tickerSymbolPage.getContent().iterator().next();
        } else {
            return null;
        }
    }
}
