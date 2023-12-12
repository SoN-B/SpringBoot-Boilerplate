package SpringGroup.SpringResult.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringGroup.SpringResult.domain.board.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
