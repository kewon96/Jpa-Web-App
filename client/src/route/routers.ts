import { createWebHistory, createRouter } from 'vue-router';

import Auth from '@/components/common/auth/AuthComponent.vue';

const routes = [
    {
        path: '/',
        name: 'auth',
        component: Auth,
    }
];

function moveRoute() {

}

export const routers = createRouter({
    history: createWebHistory(),
    routes
});