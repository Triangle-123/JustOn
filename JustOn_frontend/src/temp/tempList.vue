<template>
    <div class="container">
        <div>
            <div class="list" v-for="video in videoList" @click="selectVideo(video)">
                <span>{{ video.title }}</span>
                <span class="accordion" v-if="!video.selected"><</span>
                <span class="accordion" v-if="video.selected">></span>
                <hr>
            </div>
        </div>
        <div v-for="video in videoList">
            <VideoPlaylist v-if="video.selected" @deletePlaylist="playlistAddCount--" :count="playlistAddCount" :videoNo="video.videoNo" @open-add-playlist-view="doOpenAddPlaylist" />
        </div>
        <div v-if="videoList === ''">등록된 영상이 없습니다.</div>
    </div>
    <VideoAddPlaylist @success-add="playlistAddCount++" @close-add-playlist-view="doCloseAddPlaylist" v-if="addListView" />
</template>

<script setup>
import { ref, watch } from 'vue';
import axios from 'axios';
import VideoPlaylist from './VideoPlaylist.vue';
import VideoAddPlaylist from './VideoAddPlaylist.vue';
const addListView = ref(false);
const playlistAddCount = ref(0);

const doOpenAddPlaylist = () => {
    addListView.value = true;
}

const doCloseAddPlaylist = () => {
    addListView.value = false;
}


const videoList = ref([]);
const requestVideoList = async () => {
    const { data } = await axios.get("http://localhost:8080/api/video");
    // console.dir(data);
    videoList.value = data;
    for (const video of videoList.value) {
        video.selected = false;
    }
}
const props = defineProps(['count']);

watch(() => props.count, () => {
    requestVideoList();
});
requestVideoList();

const selectVideo = (selectedVideo) => {
    if (selectedVideo.selected) {
        selectedVideo.selected = false;
    }
    else {
        for (const video of videoList.value) {
            video.selected = false;
        }
        selectedVideo.selected = true;
    }
}
</script>

<style scoped>

.container {
    display: flex;
}

.list {
    margin: 1vh 0;
    margin-right: 2vw;
    position: relative;
    width: 33vw;
    cursor: pointer;
}

span {
    margin-right: 20px;
}

.accordion {
    position: absolute;
    right: 0;
}
</style>