package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.MenuModel;
import apap.tutorial.gopud.model.RestoranModel;
import apap.tutorial.gopud.repository.MenuDb;
import apap.tutorial.gopud.repository.RestoranDb;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MenuServiceImplTest {

    @InjectMocks
    RestoranService restoranService = new RestoranServiceImpl();

    @InjectMocks
    MenuService menuService = new MenuServiceImpl();

    @Mock
    RestoranDb restoranDb;

    @Mock
    MenuDb menuDb;

    @Test
    public void whenAddValidMenuItShouldCallMenuRepositorySave() {
        MenuModel newMenu = new MenuModel();
        newMenu.setId(1L);
        newMenu.setNama("Dummy menu");
        newMenu.setDeskripsi("Dummy deskripsi");
        newMenu.setHarga(BigInteger.valueOf(1));
        newMenu.setDurasiMasak(1);

        menuService.addMenu(newMenu);

        verify(menuDb, times(1)).save(newMenu);
    }

    @Test
    public void whenFindMenyByIdItShouldReturnTheCorrectMenu() {
        MenuModel newMenu = new MenuModel();
        newMenu.setId(1L);
        newMenu.setNama("Dummy menu");
        newMenu.setDeskripsi("Dummy deskripsi");
        newMenu.setHarga(BigInteger.valueOf(1));
        newMenu.setDurasiMasak(1);
        when(menuService.findById(1L)).thenReturn(Optional.of(newMenu));

        Optional<MenuModel> calledMenuOptional = menuService.findById(1L);

        verify(menuDb, times(1)).findById(1L);
        assertTrue(calledMenuOptional.isPresent());

        MenuModel calledMenu = calledMenuOptional.get();

        assertEquals(Long.valueOf(1), calledMenu.getId());
        assertEquals("Dummy menu", calledMenu.getNama());
        assertEquals("Dummy deskripsi", calledMenu.getDeskripsi());
        assertEquals(BigInteger.valueOf(1), calledMenu.getHarga());
        assertEquals(Integer.valueOf(1), calledMenu.getDurasiMasak());
    }

    @Test
    public void WhenFindAllMenuByIdRestoranItShouldReturnTheCorrectMenuList() {
        RestoranModel restoran = new RestoranModel();
        restoran.setIdRestoran(1L);
        List<MenuModel> menuList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MenuModel aMenu = new MenuModel();
            aMenu.setId((long) i);
            aMenu.setRestoran(restoran);
            menuList.add(aMenu);
        }
        restoran.setListMenu(menuList);
        when(menuService.findAllMenuByIdRestoran(restoran.getIdRestoran()))
                .thenReturn(menuList);

        List<MenuModel> calledMenuList = menuService.findAllMenuByIdRestoran(restoran.getIdRestoran());

        verify(menuDb, times(1)).findByRestoranIdRestoran(restoran.getIdRestoran());
        assertEquals(10, calledMenuList.size());
        for (MenuModel menu : calledMenuList) {
            assertEquals(restoran, menu.getRestoran());
        }
    }

    @Test
    public void whenDeleteMenuByIdItShouldCallMenuRepositoryDelete() {
        MenuModel sampleMenu = new MenuModel();
        sampleMenu.setId(1L);

        menuService.deleteById(sampleMenu.getId());

        verify(menuDb, times(1)).deleteById(sampleMenu.getId());
    }

    @Test
    public void whenChangeMenuCalledItShouldChangeMenuData() {
        MenuModel updatedMenu = new MenuModel();
        updatedMenu.setId(1L);
        updatedMenu.setNama("Dummy menu");
        updatedMenu.setDeskripsi("Dummy deskripsi");
        updatedMenu.setHarga(BigInteger.valueOf(1));
        updatedMenu.setDurasiMasak(1);
        when(menuDb.findById(1L)).thenReturn(Optional.of(updatedMenu));
        when(menuService.changeMenu(updatedMenu)).thenReturn(updatedMenu);

        MenuModel calledMenu = menuService.changeMenu(updatedMenu);

        verify(menuDb, times(1)).save(updatedMenu);
        assertEquals(Long.valueOf(1), calledMenu.getId());
        assertEquals("Dummy menu", calledMenu.getNama());
        assertEquals("Dummy deskripsi", calledMenu.getDeskripsi());
        assertEquals(BigInteger.valueOf(1), calledMenu.getHarga());
        assertEquals(Integer.valueOf(1), calledMenu.getDurasiMasak());
    }

    @Test
    public void whenAllMenuByRestoranIdOrderByHargaItShouldReturnOrderedMenuListByHargaAscending() {
        RestoranModel restoran = new RestoranModel();
        restoran.setIdRestoran(1L);
        List<MenuModel> menuList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MenuModel aMenu = new MenuModel();
            aMenu.setId((long) i);
            aMenu.setHarga(BigInteger.valueOf(i));
            aMenu.setRestoran(restoran);
            menuList.add(aMenu);
        }
        List<MenuModel> sortedMenuByHargaAsc = new ArrayList<>(menuList);
        sortedMenuByHargaAsc.sort(Comparator.comparing(MenuModel::getHarga));
        when(menuService.getAllByRestoranIdRestoranOrderByHargaAsc(restoran.getIdRestoran()))
                .thenReturn(sortedMenuByHargaAsc);

        List<MenuModel> calledSortedMenuList = menuService.getAllByRestoranIdRestoranOrderByHargaAsc(restoran.getIdRestoran());

        verify(menuDb, times(1))
                .getAllByRestoranIdRestoranOrderByHargaAsc(restoran.getIdRestoran());
        assertEquals(menuList.size(), calledSortedMenuList.size());
        for (int index = 0; index < calledSortedMenuList.size(); index++) {
            assertEquals(sortedMenuByHargaAsc.get(index).getHarga(), calledSortedMenuList.get(index).getHarga());
        }
    }
}
