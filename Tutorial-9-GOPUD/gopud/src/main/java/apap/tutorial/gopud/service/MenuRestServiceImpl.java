package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.MenuModel;
import apap.tutorial.gopud.repository.MenuDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class MenuRestServiceImpl implements MenuRestService {

    @Autowired
    private MenuDb menuDb;

    @Override
    public MenuModel createMenu(MenuModel menu) {
        return menuDb.save(menu);
    }

    @Override
    public MenuModel changeMenu(Long idMenu, MenuModel changedMenu) {
        Optional<MenuModel> menuOptional = menuDb.findById(idMenu);
        if (menuOptional.isPresent()) {
            MenuModel menu = menuDb.getOne(idMenu);
            menu.setNama(changedMenu.getNama());
            menu.setHarga(changedMenu.getHarga());
            menu.setDeskripsi(changedMenu.getDeskripsi());
            menu.setDurasiMasak(changedMenu.getDurasiMasak());
            menu.setRestoran(changedMenu.getRestoran());
            menuDb.save(menu);
            return menu;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public MenuModel getMenuByIdMenu(Long idMenu) {
        Optional<MenuModel> menuOptional = menuDb.findById(idMenu);
        return menuOptional.orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<MenuModel> getAllMenus() {
        return menuDb.findAll();
    }

    @Override
    public void deleteMenu(Long idMenu) {
        Optional<MenuModel> menuOptional = menuDb.findById(idMenu);
        if (menuOptional.isPresent()) {
            MenuModel deletedMenu = menuOptional.get();
            menuDb.delete(deletedMenu);
        } else {
            throw new NoSuchElementException();
        }
    }
}
