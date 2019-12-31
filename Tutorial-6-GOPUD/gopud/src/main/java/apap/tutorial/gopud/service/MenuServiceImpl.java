package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.MenuModel;
import apap.tutorial.gopud.repository.MenuDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDb menuDb;

    @Override
    public void addMenu(MenuModel menu) {
        menuDb.save(menu);
    }

    @Override
    public List<MenuModel> findAllMenuByIdRestoran(Long idRestoran) {
        return menuDb.findByRestoranIdRestoran(idRestoran);
    }

    @Override
    public Optional<MenuModel> findById(Long menuId) {
        return menuDb.findById(menuId);
    }

    @Override
    public MenuModel changeMenu(MenuModel menuModel) {
        Optional<MenuModel> targetMenuOpt = menuDb.findById(menuModel.getId());

        if (targetMenuOpt.isPresent()) {
            MenuModel targetMenu = targetMenuOpt.get();
            targetMenu.setNama(menuModel.getNama());
            targetMenu.setDeskripsi(menuModel.getDeskripsi());
            targetMenu.setHarga(menuModel.getHarga());
            targetMenu.setDurasiMasak(menuModel.getDurasiMasak());
            menuDb.save(targetMenu);
            return targetMenu;
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(Long menuId) {
        menuDb.deleteById(menuId);
    }

    @Override
    public List<MenuModel> getAllByRestoranIdRestoranOrderByHargaAsc(Long restoranId) {
        return menuDb.getAllByRestoranIdRestoranOrderByHargaAsc(restoranId);
    }
}
