package ru.yandex.practicum.order.fallback;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.order.dto.feign.ProductDto;
import ru.yandex.practicum.order.exception.ExceptionMapper;
import ru.yandex.practicum.order.exception.ProductServiceUnavailableException;
import ru.yandex.practicum.order.feign.ProductClient;

@Slf4j
@Component
public class ProductClientFallbackFactory implements FallbackFactory<ProductClient> {

	@SuppressWarnings("Convert2Lambda")
	@Override
	public ProductClient create(Throwable cause) {

		return new ProductClient() {

			@Override
			public ProductDto getProductById(Long productId) {

				if (cause instanceof FeignException ex) {
					throw ExceptionMapper.mapProductException(ex, productId);
				}
				throw new ProductServiceUnavailableException(productId, cause);
			}

		};
	}
}
