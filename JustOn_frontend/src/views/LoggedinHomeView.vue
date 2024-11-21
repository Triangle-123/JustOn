<template>
    <div class="flex">
        <div class="video">
            <MainVideoList/>
        </div>
        <div class="music">
            <MainMusicList/>
        </div>
    </div>
</template>
    
<script setup>
    import MainVideoList from '@/components/MainVideoList.vue';
    import MainMusicList from '@/components/MainMusicList.vue';
</script>
    
<style scoped>
    .flex {
        > .video {
            flex-grow: 7;
        }
        > .music {
            flex-grow: 3;
        }
    }
</style>