package gov.iti.spring.testing.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.iti.spring.testing.config.WebSecurityConfig;
import gov.iti.spring.testing.domain.Event;
import gov.iti.spring.testing.service.EventService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import static org.mockito.Mockito.when;


@WebMvcTest(EventController.class)
@Import(WebSecurityConfig.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EventController eventController;

    @MockBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getAllEvents()throws Exception {
        when(eventService.getAll()).thenReturn(List.of(new Event(), new Event()));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<Event> events = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});

        Assertions.assertThat(events.size()).isEqualTo(2);
    }
}