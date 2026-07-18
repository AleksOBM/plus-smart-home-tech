package ru.yandex.practicum.commerce.interaction.client.cart;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.interaction.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.commerce.interaction.exception.NotAuthorizedUserException;
import ru.yandex.practicum.commerce.interaction.requests.ChangeProductQuantityRequest;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("unused")
public interface ShoppingCartOperations {

	/**
	 * Получить актуальную корзину для авторизованного пользователя.
	 * <p></p>
	 *
	 * @param username Имя пользователя
	 * @return {@link ShoppingCartDto} Ранее созданная или новая,
	 * в случае ранее созданной, корзина в онлайн магазине
	 * @throws NotAuthorizedUserException Имя пользователя не должно быть пустым
	 */
	@GetMapping
	ShoppingCartDto getShoppingCart(@RequestParam @NotNull String username)
			throws NotAuthorizedUserException;

	/**
	 * Добавить товар в корзину.
	 * <p></p>
	 *
	 * @param userName        Имя пользователя
	 * @param productsInStore Отображение идентификатора товара на отобранное количество.
	 * @return {@link ShoppingCartDto} - Корзина товаров с изменениями
	 * @throws NotAuthorizedUserException Имя пользователя не должно быть пустым
	 */
	@PutMapping
	ShoppingCartDto addProductToShoppingCart(@RequestParam @NotNull String username,
	                                         @RequestBody
	                                         @NotNull
	                                         @NotEmpty
	                                         Map<@NotNull UUID, @NotNull @Positive Integer> productsInStore
	) throws NotAuthorizedUserException;

	/**
	 * Изменить количество товаров в корзине.
	 * <p></p>
	 * @param username Имя пользователя
	 * @param request Отображение идентификатора товара на отобранное количество.
	 * @return {@link ShoppingCartDto} - Корзина товаров с изменениями
	 * @throws NotAuthorizedUserException Имя пользователя не должно быть пустым
	 * @throws NoProductsInShoppingCartException Нет искомых товаров в корзине
	 */
	@PostMapping("/change-quantity")
	ShoppingCartDto changeProductQuantity(@RequestParam @NotNull String username,
	                                      @RequestBody @NotNull ChangeProductQuantityRequest request)
			throws NotAuthorizedUserException, NoProductsInShoppingCartException;

	/**
	 * Удалить указанные товары из корзины пользователя.
	 * <p></p>
	 * @param username Имя пользователя
	 * @param productIds Список идентификаторов товаров, которые нужно удалить.
	 * @return {@link ShoppingCartDto} - Обновлённое состояние корзины
	 * @throws NotAuthorizedUserException Имя пользователя не должно быть пустым
	 * @throws NoProductsInShoppingCartException Нет искомых товаров в корзине
	 */
	@PostMapping("/remove")
	ShoppingCartDto removeProductsFromShoppingCard(@RequestParam @NotNull String username,
	                                               @RequestBody @NotNull Set<@NotNull UUID> productIds)
			throws NotAuthorizedUserException, NoProductsInShoppingCartException;

	/**
	 * Деактивация корзины товаров для пользователя
	 * <p></p>
	 *
	 * @param username Имя пользователя
	 * @throws NotAuthorizedUserException Имя пользователя не должно быть пустым
	 */
	@DeleteMapping
	void deactivateShoppingCart(@RequestParam String username)
			throws NotAuthorizedUserException;
}
