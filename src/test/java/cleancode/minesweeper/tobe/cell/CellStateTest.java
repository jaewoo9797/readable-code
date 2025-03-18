package cleancode.minesweeper.tobe.cell;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CellStateTest {

    @Test
    @DisplayName("Cell State 는 초기화 시 flag 와 open 상태가 false")
    void cellState_initialize_then_Is_openAndFlag_false() {
        // given
        CellState cellState = CellState.initialize();
        // when
        boolean isOpened = cellState.isOpened();
        boolean isFlagged = cellState.isFlagged();
        // then
        assertThat(isOpened).isFalse();
        assertThat(isFlagged).isFalse();
    }

    @Test
    @DisplayName("Cell State open 메소드 실행 시 상태 변경")
    void cellState_open_then_open_is_true() {
        // given
        CellState cellState = CellState.initialize();
        // when
        cellState.open();
        // then
        assertThat(cellState.isOpened()).isTrue();
    }

    @Test
    @DisplayName("Cell State flag 메소드 실행 시 상태 true")
    void cellState_flag_then_flag_isTrue() {
        // given
        CellState cellState = CellState.initialize();
        // when
        cellState.flag();
        // then
        assertThat(cellState.isFlagged()).isTrue();
    }
}
