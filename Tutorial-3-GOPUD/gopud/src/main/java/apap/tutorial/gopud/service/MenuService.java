package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.MenuModel;

import java.util.List;
import java.util.Optional;

public interface MenuService {

    void addMenu(MenuModel menu);

    List<MenuModel> findAllMenuByIdRestoran(Long idRestoran);

    Optional<MenuModel> findById(Long menuId);

    MenuModel changeMenu(MenuModel menuModel);
}
