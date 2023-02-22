<template>
  <nav aria-label="Page navigation example">
    <ul class="inline-flex items-center -space-x-px">
      <li>
        <a
          href="#"
          class="block px-3 py-2 ml-0 leading-tight text-gray-500 bg-white border border-gray-300 rounded-l-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
        >
          <span class="sr-only">Previous</span>
          <font-awesome-icon icon="fa-solid fa-angles-left" />
        </a>
      </li>
      <li>
        <a
          href="#"
          class="block px-3 py-2 ml-0 leading-tight text-gray-500 bg-white border hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
        >
          <span class="sr-only">Previous</span>
          <font-awesome-icon icon="fa-solid fa-angle-left" />
        </a>
      </li>
      <li>
        <a href="#" class="normal">1</a>
      </li>
      <li>
        <a href="#" class="normal">2</a>
      </li>
      <li>
        <a href="#" aria-current="page" class="z-10 px-3 py-2 leading-tight dark:bg-white dark:text-zz-p">3</a>
      </li>
      <li>
        <a href="#" class="normal">4</a>
      </li>
      <li>
        <a href="#" class="normal">5</a>
      </li>
      <li>
        <a
          href="#"
          class="block px-3 py-2 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
        >
          <span class="sr-only">Next</span>
          <font-awesome-icon icon="fa-solid fa-angle-right" />
        </a>
      </li>
      <li>
        <a
          href="#"
          class="block px-3 py-2 leading-tight text-gray-500 bg-white border border-gray-300 rounded-r-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
        >
          <span class="sr-only">Next</span>
          <font-awesome-icon icon="fa-solid fa-angles-right" />
        </a>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {};
</script>

<style>
.normal {
  @apply px-3 py-2 leading-tight text-gray-500 bg-white border border-gray-300 dark:border-zz-dark-p dark:bg-zz-dark-s dark:text-gray-400;
}
</style>
