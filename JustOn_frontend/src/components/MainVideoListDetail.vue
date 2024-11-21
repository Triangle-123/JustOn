<template>
    <div class="h-[100%] bg-[#eee] rounded-[16px] px-4 py-4">
        <iframe width="100%" height="100%" :src="video.src" :title="video.title" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
    </div>
</template>
    
<script setup>
    const props = defineProps(['video']);
</script>
    
<style scoped>
    
</style>