package apap.tutorial.gopud.controller;

import apap.tutorial.gopud.model.RestoranModel;
import apap.tutorial.gopud.repository.RestoranDb;
import apap.tutorial.gopud.service.MenuService;
import apap.tutorial.gopud.service.RestoranService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestoranController.class)
public class RestoranControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestoranDb restoranDb;

    @MockBean
    @Qualifier("restoranServiceImpl")
    private RestoranService restoranService;

    @MockBean
    @Qualifier("menuServiceImpl")
    private MenuService menuService;

    private RestoranModel generateDummyRestoranModel(int count) {
        RestoranModel dummyRestoranModel = new RestoranModel();
        dummyRestoranModel.setNama("dummy " + count);
        dummyRestoranModel.setAlamat("alamat " + count);
        dummyRestoranModel.setIdRestoran((long) count);
        dummyRestoranModel.setNomorTelepon(14000);
        dummyRestoranModel.setListMenu(new ArrayList<>());

        return dummyRestoranModel;
    }

    @Test
    public void whenHomePageRouteAccessItShouldReturnStatusCode200() throws Exception {
        mockMvc.perform(get("/")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenViewAllRestoranAccessItShouldShowAllRestoranData() throws Exception {
        List<RestoranModel> allRestoranInTheDatabase = new ArrayList<>();
        for (int loopTimes = 3; loopTimes > 0; loopTimes--) {
            allRestoranInTheDatabase.add(generateDummyRestoranModel(loopTimes));
        }
        when(restoranService.getRestoranList()).thenReturn(allRestoranInTheDatabase);

        mockMvc.perform(get("/restoran/view-all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(Matchers.containsString("Daftar Seluruh Restoran")))
                .andExpect(content().string(Matchers.containsString("ID Restoran")))
                .andExpect(model().attribute("restoList", hasSize(3)))
                .andExpect(model().attribute("restoList", hasItem(
                        allOf(
                                hasProperty("idRestoran", is((long) 1)),
                                hasProperty("nama", is("dummy 1")),
                                hasProperty("alamat", is("alamat 1"))
                        )
                )))
                .andExpect(model().attribute("restoList", hasItem(
                        allOf(
                                hasProperty("idRestoran", is((long) 2)),
                                hasProperty("nama", is("dummy 2")),
                                hasProperty("alamat", is("alamat 2"))
                        )
                )))
                .andExpect(model().attribute("restoList", hasItem(
                        allOf(
                                hasProperty("idRestoran", is((long) 3)),
                                hasProperty("nama", is("dummy 3")),
                                hasProperty("alamat", is("alamat 3"))
                        )
                )));
        verify(restoranService, times(1)).getRestoranList();
    }

    @Test
    public void whenRestoranAddPostFormItShouldSuccessfullyReturnToRightView() throws Exception {
        String nama = "Dummy Restoran";
        String alamat = "Dummy Alamat";

        mockMvc.perform(post("/restoran/add")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("nama", nama)
                    .param("alamat", alamat))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-restoran"))
                .andExpect(model().attribute("namaResto", is(nama)));
    }

    @Test
    public void whenAddRestoranFormIsAccessedItShouldReturnTheCorrectView() throws Exception {
        mockMvc.perform(get("/restoran/add"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("form-add-restoran"))
                .andExpect(content().string(Matchers.containsString("Tambah Restoran")))
                .andExpect(model().attributeExists("restoran"));
    }

    @Test
    public void whenASpecificRestoranViewIsAccessedItShouldShowTheRestoranData() throws Exception {
        RestoranModel restoran = new RestoranModel();
        restoran.setIdRestoran((long) 1);
        restoran.setNama("Dummy nama");
        restoran.setAlamat("Dummy alamat");
        restoran.setNomorTelepon(420);
        when(restoranService.getRestoranByIdRestoran((long) 1)).thenReturn(Optional.of(restoran));

        mockMvc.perform(get("/restoran/view/?idRestoran=" + restoran.getIdRestoran()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("view-restoran"))
                .andExpect(model().attributeExists("resto"))
                .andExpect(model().attributeExists("menuList"))
                .andExpect(model().attribute("resto",
                        hasProperty("idRestoran", is((long) 1))))
                .andExpect(model().attribute("resto",
                        hasProperty("nama", is("Dummy nama"))))
                .andExpect(model().attribute("resto",
                        hasProperty("alamat", is("Dummy alamat"))))
                .andExpect(model().attribute("resto",
                        hasProperty("nomorTelepon", is(420))));
    }

    @Test
    public void whenChangeExistingRestoranPageIsAccessedItShouldReturnTheCorrectViewWithPopulatedFormField()
            throws Exception {
        RestoranModel restoran = new RestoranModel();
        restoran.setIdRestoran((long) 1);
        restoran.setNama("Dummy nama");
        restoran.setAlamat("Dummy alamat");
        restoran.setNomorTelepon(420);
        when(restoranService.getRestoranByIdRestoran((long) 1)).thenReturn(Optional.of(restoran));

        mockMvc.perform(get("/restoran/change/" + restoran.getIdRestoran()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("form-change-restoran"))
                .andExpect(model().attributeExists("restoran"))
                .andExpect(content().string(Matchers.containsString("Change Data Restoran")))
                .andExpect(model().attribute("restoran",
                        hasProperty("idRestoran", is((long) 1))))
                .andExpect(model().attribute("restoran",
                        hasProperty("nama", is("Dummy nama"))))
                .andExpect(model().attribute("restoran",
                        hasProperty("alamat", is("Dummy alamat"))))
                .andExpect(model().attribute("restoran",
                        hasProperty("nomorTelepon", is(420))));
    }

    @Test
    public void whenChangeNonExistingRestoranPageIsAccessedItShouldReturnTheCorrectViewWithoutForm()
            throws Exception {
        when(restoranService.getRestoranByIdRestoran((long) 1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/restoran/change/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("form-change-restoran"))
                .andExpect(model().attributeDoesNotExist("restoran"))
                .andExpect(content().string(Matchers.containsString("Restoran tidak ditemukan")));
    }

    @Test
    public void whenDeleteRestoranByIdItShouldReturnTheRightView() throws Exception {
        RestoranModel restoran = new RestoranModel();
        restoran.setIdRestoran((long) 1);
        restoran.setNama("Dummy restoran");
        restoran.setAlamat("Dummy alamat");
        restoran.setNomorTelepon(100);
        when(restoranService.getRestoranByIdRestoran((restoran.getIdRestoran()))).thenReturn(Optional.of(restoran));

        mockMvc.perform(post("/restoran/delete/" + restoran.getIdRestoran()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("delete-restoran"))
                .andExpect(model().attributeExists("restoran"));
        verify(restoranService, times(1)).deleteByIdRestoran(restoran.getIdRestoran());
    }
}
