<template>
  <div>
    <h2>座位安排</h2>

    <select v-model="selectedEmpId">
      <option disabled value="">請選擇員工</option>
      <option v-for="emp in employees" :key="emp.empId" :value="emp.empId">
        {{ emp.name }} ({{ emp.empId }})
      </option>
    </select>

    <div v-for="(floorSeats, floorNo) in groupedSeats" :key="floorNo" class="floor">
      <div class="grid">
        <div
          v-for="seat in floorSeats"
          :key="seat.floorSeatSeq"
          class="seat"
          :class="{
            occupied: isOccupied(seat.floorSeatSeq), // 已被佔用
            selected: selectedSeat === seat.floorSeatSeq // 被點選
          }"
          @click="selectSeat(seat.floorSeatSeq)"
        >
          {{ formatSeatDisplay(seat) }}
        </div>
      </div>
    </div>

    <div class="legend">
      <div><span class="seat"></span> 空位</div>
      <div><span class="seat occupied"></span> 已佔用</div>
      <div><span class="seat selected"></span> 請選擇</div>
    </div>

    <button @click="assignSeat">送出</button>
    <button @click="clearSeat" style="background-color: darkred;">
      清除座位
    </button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      seats: [],
      employees: [],
      selectedEmpId: '',
      selectedSeat: null
    };
  },
  computed: {
    // 將座位依樓層分組
    groupedSeats() {
      const groups = {};
      this.seats.forEach(seat => {
        const key = seat.floorNo;
        if (!groups[key]) groups[key] = [];
        groups[key].push(seat);
      });
      return groups;
    }
  },
  methods: {
    // 取得座位資料
    fetchSeats() {
      axios.get('http://localhost:8080/api/seats').then(res => {
        this.seats = res.data;
      });
    },
    // 取得員工資料
    fetchEmployees() {
      axios.get('http://localhost:8080/api/employees').then(res => {
        this.employees = res.data;
      });
    },
    // 取得目前佔用某座位的員工
    getOccupiedEmployee(seatId) {
      return this.employees.find(emp => emp.seat && emp.seat.floorSeatSeq === seatId);
    },
    // 判斷座位是否被佔用
    isOccupied(seatId) {
      return !!this.getOccupiedEmployee(seatId);
    },
    // 座位顯示文字
    formatSeatDisplay(seat) {
      const emp = this.getOccupiedEmployee(seat.floorSeatSeq);
      return `${seat.floorNo}樓：${seat.seatNo}${emp ? ` [員編:${emp.empId}]` : ''}`;
    },
    // 點選座位
    selectSeat(seatId) {
      this.selectedSeat = seatId;
    },
    // 指派座位給選定員工
    assignSeat() {
      if (!this.selectedEmpId || !this.selectedSeat) return;
      // 若座位已被他人佔用，不執行
      const occupiedEmp = this.getOccupiedEmployee(this.selectedSeat);
      if (occupiedEmp && occupiedEmp.empId !== this.selectedEmpId) {
        return;
      }

      axios.post('http://localhost:8080/api/seats/assign', null, {
        params: {
          empId: this.selectedEmpId,
          seatSeq: this.selectedSeat
        }
      }).then(() => {
        this.fetchEmployees();
        this.selectedSeat = null;
      });
    },
    // 清除座位佔用狀態
    clearSeat() {
      const emp = this.getOccupiedEmployee(this.selectedSeat);
      if (!emp) return;
      axios.post('http://localhost:8080/api/seats/clear', null, {
        params: {
          empId: emp.empId
        }
      }).then(() => {
        this.fetchEmployees();
        this.selectedSeat = null;
      });
    }
  },
  mounted() {
    this.fetchSeats();
    this.fetchEmployees();
  }
};
</script>

<style scoped>
.floor {
  margin-bottom: 30px;
}
.grid {
  display: grid;
  grid-template-columns: repeat(4, auto);
  gap: 10px;
  justify-content: center;
}
.seat {
  width: 160px;
  height: 50px;
  background-color: lightgray;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  border-radius: 6px;
  cursor: pointer;
}
.seat.occupied {
  background-color: #e53935;
  color: white;
}
.seat.selected {
  background-color: #90ee90;
}
.legend {
  display: flex;
  gap: 20px;
  margin: 20px 0;
}
.legend .seat {
  display: inline-block;
  width: 30px;
  height: 20px;
  margin-right: 6px;
}
button {
  padding: 6px 14px;
  font-weight: bold;
  background-color: #004080;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>
