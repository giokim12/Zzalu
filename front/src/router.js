import { createWebHistory, createRouter } from 'vue-router';
import Signup from '@/views/Accounts/SignUpView';
import FindId from '@/views/Accounts/FindIdView';
import ResetPassword from '@/views/Accounts/ResetPasswordView';

const routes = [
  {
    path: '/',
    redirect: '/main',
    // component: App,
  },
  // --------------------------------------------------------
  // ACCOUNTS

  // 로그인
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/Accounts/LoginView.vue'),
  },
  // 회원가입
  // SignUp
  {
    path: '/signup',
    name: 'signup',
    component: Signup,
    children: [
      {
        path: '',
        name: 'input-signup-info',
        component: () => import('@/components/Accounts/SignUp/InputUserInfo.vue'),
      },
      {
        path: 'email',
        name: 'input-signup-email',
        component: () => import('@/components/Accounts/InputEmail.vue'),
        props: true,
      },
      {
        path: 'code',
        name: 'input-signup-code',
        component: () => import('@/components/Accounts/InputCodeForm.vue'),
      },
      {
        path: 'complete',
        name: 'complete',
        component: () => import('@/components/Accounts/SignUp/WelcomeToZzalu.vue'),
      },
    ],
  },
  // 아이디찾기
  {
    path: '/find-id',
    name: 'find-id',
    component: FindId,
    children: [
      {
        path: '',
        name: 'find-id-input-email',
        component: () => import('@/components/Accounts/InputEmail.vue'),
      },
      {
        path: 'check-email',
        name: 'check-email',
        component: () => import('@/components/Accounts/FindId/CheckEmail.vue'),
      },
    ],
  },
  // 비번찾기(재설정)
  {
    path: '/reset-password',
    name: 'reset-password',
    component: ResetPassword,
    children: [
      {
        path: '',
        name: 'input-reset-email',
        component: () => import('@/components/Accounts/InputEmail.vue'),
        props: true,
      },
      {
        path: 'code',
        name: 'input-reset-code',
        component: () => import('@/components/Accounts/InputCodeForm.vue'),
      },
      {
        path: 'change-password',
        name: 'reset-password',
        component: () => import('@/views/Accounts/ChangePasswordView'),
      },
      {
        path: 'complete',
        name: 'reset-complete',
        component: () => import('@/components/Accounts/SignUp/WelcomeToZzalu.vue'),
      },
    ],
  },
  // 탈퇴
  {
    path: '/account-delete',
    name: 'account-delete',
    component: () => import('@/views/Accounts/AccountDeleteView'),
  },
  // 설정에서 비번변경
  {
    path: '/change-password',
    name: 'change-password',
    component: () => import('@/views/Accounts/ChangePasswordView'),
  },
  // ----------------------------------------------------------------
  // QUIET CHAT
  // 고독방 안
  {
    path: '/chat/:chat_id',
    name: 'chat',
    component: () => import('@/views/QuietChat/ChatInsideView'),
  },
  // 고독방 만들기
  {
    path: '/make-chat',
    name: 'make-chat',
    component: () => import('@/views/QuietChat/MakeChatView'),
  },
  // 채팅 리스트
  {
    path: '/chat-list',
    name: 'chat-list',
    component: () => import('@/views/QuietChat/ChatListView'),
  },
  // ------------------------------------------------------------
  // MAIN
  {
    path: '/main',
    name: 'main',
    component: () => import('@/views/MainView'),
  },
  // ------------------------------------------------------------------
  // PROFILE
  // 프로필 메인
  {
    path: '/profile/:username',
    name: 'profile',
    component: () => import('@/views/Profile/ProfileView'),
  },
  // 팔로우 팔로잉
  {
    // path: '/follow/:username',
    path: '/follow',
    name: 'follow',
    component: () => import('@/views/Profile/FollowView'),
  },
  // 설정 가는 페이지
  {
    path: '/account/settings',
    name: 'account-settings',
    component: () => import('@/views/Profile/UserSettingsView'),
  },
  // 프로필 정보 수정
  {
    path: '/edit-profile',
    name: 'edit-profile',
    component: () => import('@/views/Profile/EditProfileView'),
  },
  // -----------------------------------------------------------------
  // 제목학원
  {
    path: '/title-competition/:open_date',
    name: 'title-competition',
    component: () => import('@/views/TitleCompetitionView'),
  },
  // --------------------------------------------------------------------
  // 명예의 전당
  {
    path: '/whole-of-frame',
    name: 'whole-of-frame',
    component: () => import('@/views/WholeOfFrameView'),
  },
  // --------------------------------------------------------------------
  // 수상기록
  {
    path: '/award-record/:member_id',
    name: 'award-record',
    component: () => import('@/views/AwardRecordView'),
  },
  // --------------------------------------------------------------------
  // 짤 디테일
  {
    path: '/zzal/:zzal_id',
    name: 'zzal',
    component: () => import('@/views/ZzalDetailView'),
  },
  // 짤 업로드
  {
    path: '/zzal/create',
    name: 'create-zzal',
    component: () => import('@/views/ZzalCreateView'),
  },
  // ----------------------------------------------------------------
  // 보드 디테일
  {
    path: '/board/:board_id',
    name: 'board',
    component: () => import('@/views/BoardDetailView'),
  },
  // -----------------------------------------------------------
  // 계정 삭제
  {
    path: '/account/delete',
    name: 'delete-account',
    component: () => import('@/views/Profile/AccountDeleteView'),
  },
  // -----------------------------------------------------------
  // 관리자계정
  {
    path: '/admin',
    name: 'admin',
    component: () => import('@/views/Accounts/AdminInfoView'),
  },
  // -------------------------------------------------------------------
  // ERROR
  // 에러404 페이지X
  // {
  //   path: '/:pathMatch(.*)*',
  //   redirect: '/error-404',
  // },
  {
    path: '/error-404',
    name: 'error-404',
    component: () => import('@/views/Error/Error404View'),
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/error-404',
  },
  // 에러 500 internal server error
  {
    path: '/error-500',
    name: 'error-500',
    component: () => import('@/views/Error/Error500View'),
  },
  // 로그인 필요
  {
    path: '/login-required',
    name: 'login-required',
    component: () => import('@/views/Error/LoginRequiredView'),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior() {
    return { top: 0 };
  },
});

export default router;
