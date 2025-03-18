package cleancode.minesweeper.tobe.position;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CellPositionTest {


    @Test
    @DisplayName("Cell Position 의 인덱스가 음수일 경우 예외 발생")
    void given_index_negative_then_throw_exception() {
        // given
        // when
        assertThrows(IllegalArgumentException.class, () -> CellPosition.of(-1, 0));

        // then
    }

    @Test
    @DisplayName("Cell Position 인덱스보다 작은 값을 검사할 때 작거나 같으면 true 반환")
    void writeHereTestName() {
        // given
        CellPosition cellPosition = CellPosition.of(3, 3);
        // when
        boolean result = cellPosition.isRowIndexMoreThanOrEqual(2);
        // then
        assertThat(result).isTrue();
    }

}
