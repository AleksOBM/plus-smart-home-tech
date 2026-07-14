package ru.yandex.practicum.commerce.interaction.dto;

import lombok.Builder;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageProductDto extends PageImpl<ProductDto> {

	@Builder
	public PageProductDto(List<ProductDto> content, Pageable pageable) {
		super(content, pageable, content.size());
	}
}
