import { createWebHistory, createRouter } from 'vue-router';

import Login from '@/components/common/auth/LoginComponent.vue';
import Main from '@/components/common/main/MainComponent.vue';

const routes = [
    {
        path: '/',
        name: 'login',
        component: Login,
    },
    {
        path: '/main',
        name: 'main',
        component: Main
    }
];

function moveRoute() {

}

export const routers = createRouter({
    history: createWebHistory(),
    routes
});