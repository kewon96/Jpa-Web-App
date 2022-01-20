<template>
  <nav>
    <div class="sidebar-top">
      <span class="shrink-btn">
        <i class='bx bx-chevron-left'></i>
      </span>
<!--      <img src="./img/logo.png" class="logo" alt="">-->
      <h3 class="hide">Aqumex</h3>
    </div>

    <div class="search">
      <i class='bx bx-search'></i>
      <input type="text" class="hide" placeholder="Quick Search ...">
    </div>

    <div class="sidebar-links">
      <ul>
        <div class="active-tab"></div>
        <li class="tooltip-element" data-tooltip="0">
          <a href="#" class="active" data-active="0">
            <div class="icon">
              <i class='bx bx-tachometer'></i>
              <i class='bx bxs-tachometer'></i>
            </div>
            <span class="link hide">Dashboard</span>
          </a>
        </li>
        <li class="tooltip-element" data-tooltip="1">
          <a href="#" data-active="1">
            <div class="icon">
              <i class='bx bx-folder'></i>
              <i class='bx bxs-folder'></i>
            </div>
            <span class="link hide">Projects</span>
          </a>
        </li>
        <li class="tooltip-element" data-tooltip="2">
          <a href="#" data-active="2">
            <div class="icon">
              <i class='bx bx-message-square-detail'></i>
              <i class='bx bxs-message-square-detail'></i>
            </div>
            <span class="link hide">Messages</span>
          </a>
        </li>
        <li class="tooltip-element" data-tooltip="3">
          <a href="#" data-active="3">
            <div class="icon">
              <i class='bx bx-bar-chart-square'></i>
              <i class='bx bxs-bar-chart-square'></i>
            </div>
            <span class="link hide">Analytics</span>
          </a>
        </li>
        <div class="tooltip">
          <span class="show">Dashboard</span>
          <span>Projects</span>
          <span>Messages</span>
          <span>Analytics</span>
        </div>
      </ul>

      <h4 class="hide">Shortcuts</h4>

      <ul>
        <li class="tooltip-element" data-tooltip="0">
          <a href="#" data-active="4">
            <div class="icon">
              <i class='bx bx-notepad'></i>
              <i class='bx bxs-notepad'></i>
            </div>
            <span class="link hide">Tasks</span>
          </a>
        </li>
        <li class="tooltip-element" data-tooltip="1">
          <a href="#" data-active="5">
            <div class="icon">
              <i class='bx bx-help-circle'></i>
              <i class='bx bxs-help-circle'></i>
            </div>
            <span class="link hide">Help</span>
          </a>
        </li>
        <li class="tooltip-element" data-tooltip="2">
          <a href="#" data-active="6">
            <div class="icon">
              <i class='bx bx-cog'></i>
              <i class='bx bxs-cog'></i>
            </div>
            <span class="link hide">Settings</span>
          </a>
        </li>
        <div class="tooltip">
          <span class="show">Tasks</span>
          <span>Help</span>
          <span>Settings</span>
        </div>
      </ul>
    </div>

    <div class="sidebar-footer">
      <a href="#" class="account tooltip-element" data-tooltip="0">
        <i class='bx bx-user'></i>
      </a>
      <div class="admin-user tooltip-element" data-tooltip="1">
        <div class="admin-profile hide">
<!--          <img src="./img/face-1.png" alt="">-->
          <div class="admin-info">
            <h3>John Doe</h3>
            <h5>Admin</h5>
          </div>
        </div>
        <a href="#" class="log-out">
          <i class='bx bx-log-out'></i>
        </a>
      </div>
      <div class="tooltip">
        <span class="show">John Doe</span>
        <span>Logout</span>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">





</script>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700&display=swap");

:root {
  --main-color: #3d5af1;
  --main-color-dark: #3651d4;
  --main-color-light: #5872f5;
  --text-color: #cfcde7;
}

a {
  text-decoration: none;
}

ul {
  list-style: none;
}

nav {
  position: sticky;
  top: 0;
  left: 0;
  height: 100vh;
  background-color: var(--main-color);
  width: 16rem;
  padding: 1.8rem 0.85rem;
  color: #fff;
  display: flex;
  flex-direction: column;
  transition: width 0.5s ease-in-out;
}

.sidebar-top {
  position: relative;
  display: flex;
  align-items: center;
}

.sidebar-top .logo {
  width: 2.1rem;
  margin: 0 0.8rem;
}

.sidebar-top h3 {
  padding-left: 0.5rem;
  font-weight: 600;
  font-size: 1.15rem;
}

.shrink-btn {
  position: absolute;
  top: 50%;
  height: 27px;
  padding: 0 0.3rem;
  background-color: var(--main-color);
  border-radius: 6px;
  cursor: pointer;
  box-shadow: 0 3px 10px -3px rgba(70, 46, 118, 0.3);
  right: -2.65rem;
  transform: translateY(-50%) translateX(-8px);
  opacity: 0;
  pointer-events: none;
  transition: 0.3s;
}

.shrink-btn i {
  line-height: 27px;
  transition: 0.3s;
}

.shrink-btn:hover {
  background-color: var(--main-color-dark);
}

nav:hover .shrink-btn,
.shrink-btn.hovered {
  transform: translateY(-50%) translateX(0px);
  opacity: 1;
  pointer-events: all;
}

.search {
  min-height: 2.7rem;
  background-color: var(--main-color-light);
  margin: 2rem 0.5rem 1.7rem;
  display: grid;
  grid-template-columns: 2.7rem 1fr;
  align-items: center;
  text-align: center;
  border-radius: 50px;
  cursor: pointer;
}

.search input {
  height: 100%;
  border: none;
  background: none;
  outline: none;
  color: #fff;
  caret-color: #fff;
  font-family: inherit;
}

.search input::placeholder {
  color: var(--text-color);
}

.sidebar-links ul {
  position: relative;
}

.sidebar-links li {
  position: relative;
  padding: 2.5px 0;
}

.sidebar-links a {
  color: var(--text-color);
  font-weight: 400;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
  height: 53px;
}

.icon {
  font-size: 1.3rem;
  text-align: center;
  min-width: 3.7rem;
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 1fr;
}

.icon i {
  grid-column: 1 / 2;
  grid-row: 1 / 2;
  transition: 0.3s;
}

.icon i:last-child {
  opacity: 0;
  color: #fff;
}

.sidebar-links a.active,
.sidebar-links a:hover {
  color: #fff;
}

.sidebar-links a .link {
  transition: opacity 0.3s 0.2s, color 0.3s;
}

.sidebar-links a.active i:first-child {
  opacity: 0;
}

.sidebar-links a.active i:last-child {
  opacity: 1;
}

.active-tab {
  width: 100%;
  height: 53px;
  background-color: var(--main-color-dark);
  border-radius: 10px;
  position: absolute;
  top: 2.5px;
  left: 0;
  transition: top 0.3s;
}

.sidebar-links h4 {
  position: relative;
  font-size: 0.8rem;
  text-transform: uppercase;
  font-weight: 600;
  padding: 0 0.8rem;
  color: var(--text-color);
  letter-spacing: 0.5px;
  height: 45px;
  line-height: 45px;
  transition: opacity 0.3s 0.2s, height 0.5s 0s;
}

.sidebar-footer {
  position: relative;
  margin-top: auto;
}

.account {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.3rem;
  color: var(--text-color);
  height: 53px;
  width: 3.7rem;
  opacity: 0;
  pointer-events: none;
  transition: opacity 0.3s 0s, color 0.3s 0s;
}

.account:hover {
  color: #fff;
}

.admin-user {
  display: flex;
  align-items: center;
}

.admin-profile {
  white-space: nowrap;
  max-width: 100%;
  transition: opacity 0.3s 0.2s, max-width 0.7s 0s ease-in-out;
  display: flex;
  align-items: center;
  flex: 1;
  overflow: hidden;
}

.admin-user img {
  width: 2.9rem;
  border-radius: 50%;
  margin: 0 0.4rem;
}

.admin-info {
  padding-left: 0.3rem;
}

.admin-info h3 {
  font-weight: 500;
  font-size: 1rem;
  line-height: 1;
}

.admin-info h5 {
  font-weight: 400;
  font-size: 0.75rem;
  color: var(--text-color);
  margin-top: 0.3rem;
  line-height: 1;
}

.log-out {
  display: flex;
  height: 40px;
  min-width: 2.4rem;
  background-color: var(--main-color-dark);
  color: var(--text-color);
  align-items: center;
  justify-content: center;
  font-size: 1.15rem;
  border-radius: 10px;
  margin: 0 0.65rem;
  transition: color 0.3s;
}

.log-out:hover {
  color: #fff;
}

.tooltip {
  background-color: var(--main-color);
  position: absolute;
  right: -1.2rem;
  top: 0;
  transform: translateX(100%) translateY(-50%);
  padding: 0 0.8rem;
  font-size: 0.85rem;
  display: none;
  grid-template-rows: 1fr;
  grid-template-columns: 1fr;
  height: 30px;
  align-items: center;
  border-radius: 7px;
  box-shadow: 0 3px 10px -3px rgba(70, 46, 118, 0.3);
  opacity: 0;
  pointer-events: none;
  transition: all 0.3s;
  text-align: center;
  white-space: nowrap;
}

.tooltip span {
  grid-column: 1 / 2;
  grid-row: 1 / 2;
  opacity: 0;
  transition: 0.3s;
}

.tooltip span.show {
  opacity: 1;
}

.tooltip-element:hover ~ .tooltip {
  opacity: 1;
  pointer-events: all;
}

/* When the menu shrinks */

.hide {
  transition: opacity 0.3s 0.2s;
}

body.shrink nav {
  width: 5.4rem;
}

body.shrink .hide {
  opacity: 0;
  pointer-events: none;
  transition-delay: 0s;
}

body.shrink .shrink-btn i {
  transform: rotate(-180deg);
}

body.shrink .sidebar-links h4 {
  height: 10px;
}

body.shrink .account {
  opacity: 1;
  pointer-events: all;
  transition: opacity 0.3s 0.3s, color 0.3s 0s;
}

body.shrink .admin-profile {
  max-width: 0;
  transition: opacity 0.3s 0s, max-width 0.7s 0s ease-in-out;
}

body.shrink .tooltip {
  display: grid;
}
</style>