import { createWebHistory, createRouter } from 'vue-router';

import Auth from '@/components/common/auth/AuthComponent.vue';
// import Register from '@/components/home/RegisterComponent.vue';

const routes = [
    {
        path: '/',
        name: 'auth',
        component: Auth,
    },
    // {
    //     path: '/register',
    //     name: 'Register',
    //     component: Register
    // }
];

export const routers = createRouter({
    history: createWebHistory(),
    routes
});