package ru.yandex.practicum.commerce.interaction.dto;

import lombok.Builder;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Builder
public record PageProductDto(
		long totalElements,
		int totalPages,
		boolean first,
		boolean last,
		int size,
		List<ProductDto> content,
		int number,
		Sort sort,
		Pageable pageable,
		int numberOfElements,
		boolean empty
) {
	public static PageProductDto from(@NonNull Page<ProductDto> page) {
		return PageProductDto.builder()
				.totalElements(page.getTotalElements())
				.totalPages(page.getTotalPages())
				.first(page.isFirst())
				.last(page.isLast())
				.size(page.getSize())
				.content(page.getContent())
				.number(page.getNumber())
				.sort(page.getSort())
				.pageable(page.getPageable())
				.numberOfElements(page.getNumberOfElements())
				.empty(page.isEmpty())
				.build();
	}
}
