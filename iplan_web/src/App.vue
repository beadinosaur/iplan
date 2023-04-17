<template>
  <div id="app">
     <router-view></router-view><!--Just have a routing exit-->
     <!--Home gives an exit below -->
    <!-- So the display is with homesdsd will only show home
     And with the home path will show the home.vue home page stuff -->
    <!-- <Home /> No more home page with home.vue-->
  </div>
</template>

<script> 
export default {

}
</script>

<style lang="scss">
@import url('./assets/css/reset.css');
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
