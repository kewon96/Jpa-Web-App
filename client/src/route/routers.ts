import { createWebHistory, createRouter } from 'vue-router';

import Auth from '@/components/common/auth/AuthComponent.vue';
import EmailAuth from '@/components/common/auth/EmailAuth.vue';

const routes = [
    {
        path: '/',
        name: 'auth',
        component: Auth,
    },
    {
        path: '/email/auth',
        name: 'emailAuth',
        component: EmailAuth
    }
];

function moveRoute() {

}

export const routers = createRouter({
    history: createWebHistory(),
    routes
});