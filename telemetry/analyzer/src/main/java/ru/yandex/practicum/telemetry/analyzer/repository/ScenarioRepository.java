package ru.yandex.practicum.telemetry.analyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.telemetry.analyzer.model.Scenario;

import java.util.Optional;
import java.util.Set;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {

	Set<Scenario> findByHubId(String hubId);

	Optional<Scenario> findByHubIdAndName(String hubId, String name);

	boolean existsByHubIdAndName(String hubId, String name);

	void deleteByHubIdAndName(String hubId, String name);
}
