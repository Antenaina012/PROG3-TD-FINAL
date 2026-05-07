package org.example.examprog3;

import org.example.examprog3.Service.CollectivityService;
import org.example.examprog3.Service.MemberService;
import org.example.examprog3.controller.CollectivityController;
import org.example.examprog3.controller.MemberController;
import org.example.examprog3.entity.Collectivity;
import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.enums.Gender;
import org.example.examprog3.entity.enums.MemberOccupation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CollectivityController.class, MemberController.class})
class ApplicationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollectivityService collectivityService;

    @MockBean
    private MemberService memberService;

    @Test
    void shouldReturnCollectivitiesList() throws Exception {
        when(collectivityService.getAllCollectivities()).thenReturn(List.of(
                new Collectivity(UUID.randomUUID().toString(), "Collectivity Test", "Antananarivo", "Riziculture", LocalDate.now())
        ));

        mockMvc.perform(get("/collectivities"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnMembersList() throws Exception {
        when(memberService.getAllMembers()).thenReturn(List.of(
                new Member(UUID.randomUUID().toString(), "Jean", "Rabe", LocalDate.of(1990, 1, 1), Gender.MALE, "Antananarivo", "Agriculteur", "+261330000000", "jean.rabe@example.com", MemberOccupation.JUNIOR, LocalDate.now(), null)
        ));

        mockMvc.perform(get("/members"))
                .andExpect(status().isOk());
    }
}
