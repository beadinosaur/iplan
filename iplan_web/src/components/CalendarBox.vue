<template>
    <div class="calendar-box" :class="{ isopen: calendarIsOpen }">
      <!-- Expand and close the box, using the hidden property to add a mask to the inner element-->
      <div class="base-box" :class="{ isopen: calendarIsOpen }">
        <!-- Relative positioning of the box -->
        <div class="calendar-body" :class="{ isopen: calendarIsOpen }">
          <!-- Date title and toggle button -->
          <div class="calendar-body-title">
            <div class="arrow-box" @click="handlePreMonth">
              <svg-icon id="left-arrow" icon-class="icon-jiantou"></svg-icon>
            </div>
            <div class="title-text">
              {{ year + "年" + (month + 1) + "月" + day + "日" }}
            </div>
            <div class="arrow-box" @click="handleNextMonth">
              <svg-icon id="right-arrow" icon-class="icon-jiantou"></svg-icon>
            </div>
          </div>
          <!-- Calendar body -->
          <div class="calendar-body-main">
            <div
              v-for="oneDay in visibleCalendar"
              :key="oneDay.id"
              class="day-box"
              :class="{
                notnormal: visibleCalendar[oneDay.id].type !== 'normal',
                selectDay: highLight == oneDay.id,
              }"
              @click="selectMethods(oneDay)"
            >
              <span class="day-num">{{ oneDay.content }}</span>
              <span class="is-has" :class="{ checked: isHasTodo(oneDay) }"></span>
            </div>
          </div>
        </div>
        <!-- week -->
        <div class="week" :class="{ isopen: calendarIsOpen }">
          <span class="week-title" v-for="(d, index) in week" :key="index">{{
            d
          }}</span>
        </div>
        <!-- Expand and close buttons -->
        <div class="calendar-footer" :class="{ isopen: calendarIsOpen }">
          <div @click="switchBox">
            <svg
              class="svg"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
              viewBox="0 0 60 16"
              width="60px"
              height="16px"
            >
              <path id="open-btn" :d="openBtnPath" />
            </svg>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import CalendarAlgorithm from "./CalendarAlgorithm";
  
  export default {
    // The calendar logic is in a separate js file
    mixins: [CalendarAlgorithm],
    name: "CalendarBox",
    data() {
      return {
        // week
        week: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
        // Box switch state
        calendarIsOpen: false,
        // When clicked, the box's id is stored in this property, which is used to determine which day it should be highlighted
        highLight: null,
        // Gets the style object used to modify css variables
        declaration: document.styleSheets[0].cssRules[0].style,
        // Opens the path to the close box button
        openBtnPath: `M 16 8 L 30 8 L 44 8 Z`,
      };
    },
    computed: {
      // Calendar box displacement parameter
      boxDisplacement() {
        return parseInt(this.highLight / 7);
      },
      // The timestamp of the currently selected date
      selectTimestamp() {
        return this.dayjs(new Date(this.year, this.month, this.day)).valueOf();
      },
    },
    methods: {
      // Whether the current selected date has a backlog
      isHasTodo(oneDay) {
        return this.$store.state.todoListsAll.filter(
          (list) => list.timestamp == oneDay.timestamp
        ).length;
      },
      // Open and close the calendar box
      switchBox() {
        this.calendarIsOpen = !this.calendarIsOpen;
      },
      // Select a date
      selectMethods(oneDay) {
        this.year = oneDay.year;
        this.month = oneDay.month;
        this.day = oneDay.content;
        this.highLight = this.day + this.targetDay - 1;
        this.$store.commit("updateSelect", this.selectTimestamp);
      },
      // Switch to last month
      handlePreMonth() {
        if (this.month === 0) {
          this.year = this.year - 1;
          this.month = 11;
        } else {
          this.month = this.month - 1;
        }
        this.day = 1;
        this.highLight = this.targetDay;
        this.$store.commit("updateSelect", this.selectTimestamp);
      },
      // Switch to next month
      handleNextMonth() {
        if (this.month === 11) {
          this.year = this.year + 1;
          this.month = 0;
        } else {
          this.month = this.month + 1;
        }
        this.day = 1;
        this.highLight = this.targetDay;
        this.$store.commit("updateSelect", this.selectTimestamp);
      },
      // Back to today
      selectInit() {
        this.year = this.dayjs().year();
        this.month = this.dayjs().month();
        this.day = this.dayjs().date();
        this.highLight = this.day + this.targetDay - 1;
        const today = {
          year: this.dayjs().year(),
          month: this.dayjs().month(),
          day: this.dayjs().date(),
        };
        this.$store.commit("updateSelect", this.dayjs(today).valueOf());
      },
    },
    mounted() {
      // The initialization date is selected
      this.selectInit();
    },
    updated() {
      
      this.declaration.setProperty("--calendar-1", this.boxDisplacement);
    },
  };
  </script>
  
  <style scoped>
  :root {
    --calendar-1: 0;
  }
  /* Bigbox */
  .calendar-box {
    z-index: 10;
    position: relative;
    display: flex;
    justify-content: center;
    min-width: 44.5rem;
    height: 7rem;
    border-radius: 16px;
    background-color: var(--BG);
  }
  /* week */
  .week {
    z-index: 3;
    position: absolute;
    top: 0.6rem;
    display: flex;
    align-items: center;
    flex-wrap: nowrap;
    transition: 0.6s ease-in-out;
  }
  /* Weekly script */
  .week-title {
    flex-shrink: 0;
    color: var(--Gray-6);
    font-size: 0.75rem;
    line-height: 1rem;
    margin: 0 1.75rem;
  }
  /* Relative positioning box */
  .calendar-body {
    position: relative;
    /* Variable driven relative positioning */
    top: calc(((var(--calendar-1) * 6rem + 3rem) * -1));
    width: 35rem;
    height: 38.5rem;
    transition: 0.6s ease-in-out;
  }
  /* Calendar title box */
  .calendar-body-title {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 35rem;
    height: 2rem;
    margin-bottom: 3rem;
  }
  /* The box that wraps the arrow */
  .arrow-box {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 2rem;
    height: 2rem;
    margin-left: 6rem;
    border-radius: 1rem;
  }
  .arrow-box:nth-last-of-type(1) {
    margin-right: 6rem;
    margin-left: 0;
  }
  .arrow-box:hover {
    background-color: var(--Gray-2);
  }
  .arrow-box:active {
    background-color: var(--Theme-6);
  }
  /* arrows */
  #left-arrow {
    color: var(--Gray-6);
    width: 1.2rem;
    height: 1.2rem;
  }
  #right-arrow {
    transform: rotate(180deg);
    color: var(--Gray-6);
    width: 1.2rem;
    height: 1.2rem;
  }
  .arrow-box:hover #left-arrow,
  .arrow-box:hover #right-arrow {
    color: var(--Gray-8);
  }
  .arrow-box:active #left-arrow,
  .arrow-box:active #right-arrow {
    color: var(--BG);
  }
  /* Calendar title text */
  .title-text {
    color: var(--Gray-7);
    font-size: 1.2rem;
    line-height: 2rem;
    font-weight: 600;
    letter-spacing: 1px;
  }
  /* Calendar body */
  .calendar-body-main {
    display: flex;
    flex-wrap: wrap;
    align-content: flex-start;
    width: 35rem;
    height: 34rem;
  }
  /* day smallbox */
  .day-box {
    display: inline-flex;
    flex-direction: column;
    width: 4rem;
    height: 4rem;
    margin: 0 0.5rem 2rem;
    cursor: default;
  }
  /* day number */
  .day-num {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 3rem;
    height: 3rem;
    margin: 0 0.5rem;
    border-radius: 1.5rem;
    color: var(--Gray-8);
    font-size: 1.5rem;
    line-height: 2rem;
    font-weight: 600;
    transition: 0.1s;
  }
  .day-num:hover {
    background-color: var(--Theme-1);
  }
  .notnormal .day-num {
    color: var(--Gray-6);
  }
  .selectDay .day-num {
    color: var(--BG);
    background-color: var(--Theme-6);
  }
  /* To-do mark */
  .is-has {
    width: 6px;
    height: 6px;
    margin: 0.3125rem 1.8125rem;
    border-radius: 3px;
  }
  /* The todo tag is activated*/
  .is-has.checked {
    background-color: var(--Theme-4);
  }
  /* Wrap a large box with a button to expand the box */
  .calendar-footer {
    z-index: 2;
    position: absolute;
    top: 6rem;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 35rem;
    height: 1rem;
  }
  .calendar-footer > div {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 60px;
    height: 16px;
    cursor: pointer;
  }
 
  #open-btn {
    stroke: var(--Gary-6);
    stroke-width: 4px;
    fill: none;
  }
  .calendar-footer > div:hover .calendar-is-open {
    background-color: var(--Theme-2);
  }
  
  .base-box {
    position: absolute;
    overflow: hidden;
    display: flex;
    justify-content: center;
    min-width: 44.5rem;
    height: 7rem;
    border-radius: 16px;
    background-color: var(--BG);
    box-shadow: 0 0 0 0 rgba(148, 163, 184, 0);
    transition: 0.6s ease-in-out;
  }

  .base-box.isopen {
    min-width: 44.5rem;
    height: 44rem;
    box-shadow: 0 16px 36px 0 rgba(148, 163, 184, 0.2);
  }

  .calendar-body.isopen {
    top: 2rem;
  }

  .week.isopen {
    top: 5.5rem;
  }
  /* Expand and move the button up */
  .calendar-footer.isopen {
    top: 0.5rem;
  }
  
  @media (min-width: 1280px) {
    .calendar-box {
      min-width: 36.5rem;
    }
    .base-box {
      min-width: 36.5rem;
    }
    .base-box.isopen {
      min-width: 42.5rem;
    }
  }
  </style>