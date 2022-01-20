import { createWebHistory, createRouter } from 'vue-router';

import Login from '@/components/common/auth/LoginComponent.vue';
import Main from '@/components/common/main/MainComponent.vue';

const routes = [
    {
        path: '/',
        name: 'main',
        component: Main
    },
    {
        path: '/login',
        name: 'login',
        component: Login,
    },
];

export const routers = createRouter({
    history: createWebHistory(),
    routes
});