package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.MenuModel;

import java.util.List;

public interface MenuRestService {

    MenuModel createMenu(MenuModel menu);

    MenuModel changeMenu(Long idMenu, MenuModel changedMenu);

    MenuModel getMenuByIdMenu(Long idMenu);

    List<MenuModel> getAllMenus();

    void deleteMenu(Long idMenu);
}
