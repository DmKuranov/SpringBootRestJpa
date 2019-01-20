package ru.dmkuranov.springbootrestjpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.dmkuranov.springbootrestjpa.account.dto.AmountAndTargetAccountDto;
import ru.dmkuranov.springbootrestjpa.account.dto.AmountDto;
import ru.dmkuranov.springbootrestjpa.account.dto.AccountDto;
import ru.dmkuranov.springbootrestjpa.service.AbstractTest;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RestApiTest extends AbstractTest {
    @Autowired
    WebApplicationContext webApplicationContext;

    protected MockMvc mvc;
    protected ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void deposit() throws Exception {
        MvcResult mvcResult = makeRequestWithAmount(100L, AmountDto.of("15"), "deposit");
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
        AccountDto accountDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AccountDto.class);
        Long expectedAmount = 100L;
        Assert.assertEquals(expectedAmount, accountDto.getNumber());
        Assert.assertThat(new BigDecimal("15"), Matchers.comparesEqualTo(accountDto.getAmount()));
    }

    @Test
    public void withdraw() throws Exception {
        final Long accountNo = 101L;
        MvcResult mvcResult = makeRequestWithAmount(accountNo, AmountDto.of("15"), "withdraw");
        Assert.assertEquals(404, mvcResult.getResponse().getStatus());

        mvcResult = makeRequestWithAmount(accountNo, AmountDto.of("15"), "deposit");
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());

        mvcResult = makeRequestWithAmount(accountNo, AmountDto.of("10"), "withdraw");
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
        AccountDto accountDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AccountDto.class);
        Assert.assertThat(new BigDecimal("5"), Matchers.comparesEqualTo(accountDto.getAmount()));
        Assert.assertThat(accountNo, Matchers.comparesEqualTo(accountDto.getNumber()));

        mvcResult = makeRequestWithAmount(accountNo, AmountDto.of("10"), "withdraw");
        Assert.assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    public void transfer() throws Exception {
        final Long accountNo = 102L;
        MvcResult mvcResult = makeRequestWithAmount(accountNo, AmountDto.of("15"), "deposit");
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());

        AmountAndTargetAccountDto dto = AmountAndTargetAccountDto.of("7.6", 103L);
        ;
        mvcResult = makeRequestWithAmount(accountNo, dto, "transfer");
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
        AccountDto accountDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AccountDto.class);
        Assert.assertThat(new BigDecimal("7.4"), Matchers.comparesEqualTo(accountDto.getAmount()));

        mvcResult = makeRequestWithAmount(accountNo, dto, "transfer");
        Assert.assertEquals(400, mvcResult.getResponse().getStatus());
    }

    private MvcResult makeRequestWithAmount(Long accountNo, Object requestDto, String action) throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.post("/accounts/" + accountNo + "/" + action)
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto));
        return mvc.perform(builder).andReturn();
    }

}
