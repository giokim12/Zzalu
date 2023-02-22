// /** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  darkMode: 'class', // or 'media' or 'class'
  theme: {
    extend: {
      colors: {
        // 배경색깔
        'zz-p': '#AA8AD4',
        'zz-s': '#6750A4',
        'zz-light-p': '#3D2C6A',
        'zz-light-s': '#D2B0FF',
        'zz-dark-p': '#977CDE',
        'zz-dark-s': '#7D62A1',
        'zz-light-input': '#EDEEFF',
        'zz-dark-input': '#4B4550',
        'zz-negative': '#C0C0C0',
        // 글씨 색깔
        'zz-error': '#FF0000',
        'zz-correct': '#58A24C',
        'zz-edit': '#3C88E7',
        'zz-darkgray': '#646464',
        // 메달 색깔
        'zz-gold': '#EBD516',
        'zz-silver': '#A7A7A7',
        'zz-bronze': '#948150',
        // 디바이더 색깔, 백그라운드 색깔
        'zz-bd': '#292A2D',
        'zz-dark-div': '#6A6A6A',
        'zz-light-div': '#E9E9E9',
      },
      spacing: {
        'nav-height': '3.2rem',
        'divider-height': '0.5px',
      },
    },
    fontFamily: {
      carter: ['Carter', 'Carter-One'],
      spoq: ['Spoqa Han Sans Neo', 'sans-serif'],
      bhs: ['Black-Han-Sans', 'sans-serif'],
      pop: ['Poppins', 'sans-serif'],
    },
  },
  variants: {
    extend: {},
  },
  plugins: [require('@tailwindcss/line-clamp')],
};
