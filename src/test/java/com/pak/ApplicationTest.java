package com.pak;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pak.controller.CallbackController;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(CallbackController.class)
public class ApplicationTest {
    @MockBean
    private CallbackController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnConfirmation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/callback")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"type\":\"confirmation\",\"group_id\":196756342}")
                    .characterEncoding("utf-8")
                    )
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(content().string(containsString("841d1022")));
    }

    @Test
    public void shouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/callback")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"type\":\"message_new\",\"object\":{\"message\":{\"date\":1594290965,\"from_id\":57821415,\"id\":219,\"out\":0,\"peer_id\":57821415,\"text\":\"hello world\",\"conversation_message_id\":170,\"fwd_messages\":[],\"important\":false,\"random_id\":0,\"attachments\":[],\"is_hidden\":false},\"client_info\":{\"button_actions\":[\"text\",\"vkpay\",\"open_app\",\"location\",\"open_link\"],\"keyboard\":true,\"inline_keyboard\":true,\"carousel\":false,\"lang_id\":0}},\"group_id\":196756342,\"event_id\":\"d69626e8a1d86d7fa5f4938c76fc4499f8b071df\"}")
                .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(content().string(containsString("ok")));
    }

}
