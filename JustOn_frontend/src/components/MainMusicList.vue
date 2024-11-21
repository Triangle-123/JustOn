<template>
    <div class="content-wrap">
        <h2>음악</h2>
        <div id="player"></div>
        <div class="controls">
            <div class="selected">
                <span class="">
                    {{ selectedMusic.title }}
                </span>
            </div>
            <div>
                <span v-if="currentTime">
                    <span v-if="curHour"> {{ curHour }} :</span>
                    <span v-if="curMin"> {{ curMin }} :</span>
                    {{ curSec }}
                </span>
                <input type="range" :value="currentTime" :max="duration" @input="changeTime"
                    @mousedown="startUserInteraction" @mouseup="endUserInteraction">
                <!-- <progress class="progressTag" :value="currentTime" :max="duration"></progress> -->
                <span v-if="duration">
                    <span v-if="totHour"> {{ totHour }} :</span>
                    <span v-if="totMin">{{ totMin }} :</span>
                    {{ totSec }}
                </span>
            </div>
            <div class="control-bar">
                <button @click="playVideo"><i class="bi bi-play-fill"></i></button>
                <button @click="pauseVideo"><i class="bi bi-pause-fill"></i></button>
                <button @click="mute" v-if="!isMuted"><i class="bi bi-volume-up-fill"></i></button>
                <button @click="unMute" v-if="isMuted"><i class="bi bi-volume-mute"></i></button>
                <input type="range" min="0" max="100" v-model="volume" @input="changeVolume">
                <button @click="prevMusic"><i class="bi bi-skip-start-fill"></i></button>
                <button @click="nextMusic"><i class="bi bi-skip-end-fill"></i></button>
            </div>
        </div>
        <i class="bi bi-music-note-list"></i>
        <select @change="requestGetMusic">
            <option v-for="playlist in musicPlaylist" :value="playlist.playlistName" :key="playlist.playlistName">{{
                playlist.playlistName }}
            </option>
        </select>
        <div class="list p-3 flex justify-between items-center relative border-solid border-gray-200 border-b rounded-[16px] hover:bg-[#f6f6f6] cursor-pointer"
            v-for="(music, index) in musicInPlaylist" @click="musicSelect(index)" :key="music.musicNo">

            <span class="ellipsis max-width-[300px] inline-block">{{
                music.title
            }}</span>

        </div>
    </div>
</template>

<!-- <script src="https://www.youtube.com/iframe_api"></script> -->
<script setup>
import { onMounted, onBeforeMount, ref, watch } from 'vue';
import axios from '@/axios/index';
const musicPlaylist = ref([]);
const musicInPlaylist = ref([]);
const selectedMusic = ref({});
const selectedIndex = ref(0);
const isMuted = ref(false);
const currentTime = ref(0);
const duration = ref(0);

const curHour = ref();
const curMin = ref();
const curSec = ref();
const totHour = ref();
const totMin = ref();
const totSec = ref();

const requestGetPlaylist = async () => {
    const response = await axios.get('api-music/playlist');
    // console.log(response);
    musicPlaylist.value = response.data;
    requestGetMusic(musicPlaylist.value[0]);
}

const requestGetMusic = async (event) => {
    const playlistName = ref("");
    if (!event.target) {
        playlistName.value = event.playlistName;
    } else playlistName.value = event.target.value;
    const response = await axios.get(`api-music/${playlistName.value}/music`);
    musicInPlaylist.value = response.data;
    selectedMusic.value = musicInPlaylist.value[0];
    selectedIndex.value = 0;

    console.log("load")
    console.log(YT.Player);
    loadVideo();
    player.value.stopVideo();
}

const player = ref();
const volume = ref(100);
const isApiLoaded = ref(false);

const getTime = () => {
    if (!isUserInteracting.value) {
        currentTime.value = player.value.getCurrentTime();
        curHour.value = undefined;
        curMin.value = undefined;
        curSec.value = undefined;
        if (currentTime.value >= (60 * 60)) {
            curHour.value = Math.floor((currentTime.value / (60 * 60)));
            curMin.value = Math.floor((currentTime.value - (curHour.value * 60 * 60)) / 60);
            curSec.value = String(Math.floor(currentTime.value - (curHour.value * 60 * 60) - (curMin.value * 60))).padStart(2, '0');
            curMin.value = String(curMin.value).padStart(2, '0');
            if (totHour.value) {
                if (totHour.value >= 10) {
                    curHour.value = String(curHour.value).padStart(2, '0');
                }
            }
        }
        else if (currentTime.value >= 60) {
            if (totHour.value) {
                if (totHour.value >= 10) {
                    curHour.value = '00';
                } else curHour.value = '0';
            }
            curMin.value = Math.floor(currentTime.value / 60);
            curSec.value = String(Math.floor(currentTime.value - (curMin.value * 60))).padStart(2, '0');
            if (totMin.value) {
                if (totMin.value >= 10 || totMin.value.length == 2) {
                    curMin.value = String(curMin.value).padStart(2, '0');
                }
            }
        } else {
            if (totHour.value) {
                if (totHour.value >= 10) {
                    curHour.value = '00';
                } else curHour.value = '0';
            }
            if (totMin.value) {
                if (totMin.value >= 10 || totMin.value.length == 2) {
                    curMin.value = '00';
                }
            }
            else curMin.value = '0';
            curSec.value = String(Math.floor(currentTime.value)).padStart(2, '0');
        }
        if(duration.value > 0) {
            if(Math.floor(currentTime.value) >= Math.floor(duration.value)) {
                nextMusic();
            }
        }
    }
}

const isUserInteracting = ref(false); // 사용자 조작 플래그
// 사용자가 슬라이더를 조작할 때
const changeTime = (event) => {
    currentTime.value = event.target.value; // 조작한 값을 현재 시간에 반영
    curHour.value = undefined;
        curMin.value = undefined;
        curSec.value = undefined;
        if (currentTime.value >= (60 * 60)) {
            curHour.value = Math.floor((currentTime.value / (60 * 60)));
            curMin.value = Math.floor((currentTime.value - (curHour.value * 60 * 60)) / 60);
            curSec.value = String(Math.floor(currentTime.value - (curHour.value * 60 * 60) - (curMin.value * 60))).padStart(2, '0');
            curMin.value = String(curMin.value).padStart(2, '0');
            if (totHour.value) {
                if (totHour.value >= 10) {
                    curHour.value = String(curHour.value).padStart(2, '0');
                }
            }
        }
        else if (currentTime.value >= 60) {
            if (totHour.value) {
                if (totHour.value >= 10) {
                    curHour.value = '00';
                } else curHour.value = '0';
            }
            curMin.value = Math.floor(currentTime.value / 60);
            curSec.value = String(Math.floor(currentTime.value - (curMin.value * 60))).padStart(2, '0');
            if (totMin.value) {
                if (totMin.value >= 10 || totMin.value.length == 2) {
                    curMin.value = String(curMin.value).padStart(2, '0');
                }
            }
        } else {
            if (totHour.value) {
                if (totHour.value >= 10) {
                    curHour.value = '00';
                } else curHour.value = '0';
            }
            if (totMin.value) {
                if (totMin.value >= 10 || totMin.value.length == 2) {
                    curMin.value = '00';
                }
            }
            else curMin.value = '0';
            curSec.value = String(Math.floor(currentTime.value)).padStart(2, '0');
        }
};

// 조작 시작
const startUserInteraction = () => {
    isUserInteracting.value = true;
};

// 조작 종료
const endUserInteraction = () => {
    player.value.seekTo(currentTime.value);
    player.value.playVideo();
    isUserInteracting.value = false;
};

const timer = ref();
const playVideo = () => {
    player.value.playVideo();
    clearInterval(timer.value);
    setTimeout(() => {
        totHour.value = undefined;
        totMin.value = undefined;
        totSec.value = undefined;
        duration.value = player.value.getDuration();
        if (duration.value >= (60 * 60)) {
            totHour.value = Math.floor((duration.value / (60 * 60)));
            totMin.value = Math.floor((duration.value - (totHour.value * 60 * 60)) / 60);
            totSec.value = String(Math.floor(duration.value - (totHour.value * 60 * 60) - (totMin.value * 60))).padStart(2, '0');
            totMin.value = String(totMin.value).padStart(2, '0');
        }
        else if (duration.value >= 60) {
            totMin.value = Math.floor(duration.value / 60);
            totSec.value = String(Math.floor(duration.value - (totMin.value * 60))).padStart(2, '0');
        } else {
            totSec.value = String(Math.floor(duration.value)).padStart(2, '0');
        }
        // console.log(duration.value, totHour.value, totMin.value, totSec.value);
    }, 1000)
    timer.value = setInterval(getTime, 1000);
}
const pauseVideo = () => {
    player.value.pauseVideo();
}
const changeVolume = () => {
    player.value.setVolume(volume.value);
}
const mute = () => {
    player.value.mute();
    isMuted.value = true;
}
const unMute = () => {
    player.value.unMute();
    isMuted.value = false;
}

const loadVideo = () => {
    player.value.loadVideoById({
        videoId: selectedMusic.value.musicId,
        startSeconds: 0,
        endSeconds: 0,
        suggestedQuality: 'large'
    });
}

const musicSelect = (index) => {
    selectedMusic.value = musicInPlaylist.value[index];
    selectedIndex.value = index;
    loadVideo();
    playVideo();
}

const prevMusic = () => {
    if (selectedIndex.value == 0) {
        musicSelect(musicInPlaylist.value.length - 1);
    } else musicSelect(selectedIndex.value - 1);
}

const nextMusic = () => {
    if (selectedIndex.value == musicInPlaylist.value.length - 1) {
        musicSelect(0);
    } else musicSelect(selectedIndex.value + 1);
}

const loadYouTubeAPI = async () => {
    return new Promise((resolve) => {
        if (typeof YT === "undefined" || typeof YT.Player === "undefined") {

            const script = document.createElement("script");
            script.src = "https://www.youtube.com/iframe_api";
            script.async = true;
            script.defer = true;
            document.body.appendChild(script);
            console.log("api");
            // API 로드 후 초기화 함수 연결

            window.onYouTubeIframeAPIReady = async () => {
                isApiLoaded.value = true;
                resolve();
            }
            
        } else {
            isApiLoaded.value = true;
            resolve();
            return;
        }
    })
}

const initializePlayer = () => {
    return new Promise((resolve) => {
        if (isApiLoaded.value) {
            player.value = new YT.Player("player", {
                height: "0",
                width: "0",
                videoId: '',
                events: {
                    onReady: (event) => {
                        console.log("init");
                        event.target.setVolume(volume.value);
                        resolve();
                    },
                },
            });
        }
    })
}

watch(() => isApiLoaded.value, () => {
    console.log("YT load")
    console.log(YT);
    console.log(YT.Player);
})
onBeforeMount(() => {
    loadYouTubeAPI()
        .then(() => initializePlayer())
        .then(() => requestGetPlaylist())
        .catch((error) => {
            console.log(error);
        })
})
</script>

<style scoped>
span {
    max-width: 300px;
}


.selected {

    max-width: 200px;
    overflow: hidden;
    /* position: relative; */

    >span {
        max-width: 200px;
        display: inline-block;
        white-space: nowrap;
        animation: flowText 8s linear infinite;

    }
}

/* .selected::after {
    content: "";
    position: absolute;
    right: 0;
    height: 100%;
    pointer-events: none;
    background-image: linear-gradient(to left, transparent, white);
} */


@keyframes flowText {
    0% {
        transform: translateX(calc(100%));
    }

    100% {
        transform: translateX(calc(-200% - 400px));
    }
}


.controls {
    background-color: #eee;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-radius: 600px;

    >.control-bar {

        >button,
        input {
            margin: 0 0.2vw;
        }
    }
}
</style>