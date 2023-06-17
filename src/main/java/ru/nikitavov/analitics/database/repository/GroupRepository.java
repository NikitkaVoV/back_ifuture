package ru.nikitavov.analitics.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitavov.analitics.database.model.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}