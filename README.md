# NOTI-MOTE

- 리모콘 기능을 안드로이드 휴대폰 알림창에 구현.

- 리모콘 기능 중 가장 많이 쓰이는 기능 → 전원, 음량, 채널, 홈, 재생 등...

- 모듈화를 통해 전원을 제외한 기능들 추가/삭제 가능.

## Notimote 사용법

### Notimote 생성

```kotlin
val notimote: Notimote = Notimote()
```

### Notimote 초기화

```kotlin
notimote.init {
                with { this@MainActivity } // 필수
                receiverClass { NotimoteReceiver::class.java } // 필수
                notificationManager { notificationManager } // 필수
                notificationChannel { channel } // 필수
                initTextPlaylist { "Playlist" }
                setLayoutVisible(
                    arrayOf(
                        Notimote.SOUND, //추가,삭제 가능
                        Notimote.CHANNEL, //추가,삭제 가능
                        Notimote.PLAYSTOP, //추가,삭제 가능
                        Notimote.HOME //추가,삭제 가능
                    ),
                    View.VISIBLE // VISIBLE or GONE ...
                )
            }
```

### NotificationManager 생성

```kotlin
val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
```

### NotificationChannel 생성

1. Oreo 이상

```kotlin
val name = "Notimote"
val importance = NotificationManager.IMPORTANCE_DEFAULT
val channel = NotificationChannel(NOTIMOTE_CHANNEL.toString(), name, importance)

notimote.init {
    ...
    notificationChannel{ channel }
}
```

또는

채널 자동 생성

```kotlin
notimote.init {
    ...
    notificationChannel(id, name, importance)
}
```

2. 이하 버전

```kotlin
notimote.init {
    ...
    channel{ "channel" }
}
```

## setLayoutVisible의 특징

알림창 길이의 한계가 있기 때문에 아래의 보기 중 최대 3가지만 선택하는 것이 좋음.

- Notimote.SOUND
- Notimote.CHANNEL
- Notimote.PLAYSTOP
- Notimote.HOME

## 아이콘 변경

```kotlin
notimote.init {
    ...
    iconID{ R.drawable.icon }
}
```

## NotimoteReceiver 사용하기

알림창 버튼을 사용하기 위해 NotimoteReceiver가 필요하다.  
NotimoteReceiver를 상속한 뒤 아래를 따라하자.

```kotlin
class MainReceiver : NotimoteReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            Notimote.POWER_BUTTON -> {
                // 전원 버튼 클릭 시 사용할 함수
            }
            ...
        }
    }
}
```

아래의 변수를 사용해 버튼 클릭 이벤트를 가져올 수 있다.

1. POWER

- Notimote.POWER_BUTTON

2. SOUND

- Notimote.SOUND_BUTTON_UP
- Notimote.SOUND_BUTTON_DOWN

3. CHANNEL

- Notimote.CHANNEL_BUTTON_UP
- Notimote.CHANNEL_BUTTON_DOWN

4. PLAYSTOP

- Notimote.PLAYSTOP_BUTTON_REWIND
- Notimote.PLAYSTOP_BUTTON_STOP
- Notimote.PLAYSTOP_BUTTON_PLAY
- Notimote.PLAYSTOP_BUTTON_FORWARD

5. HOME

- Notimote.HOME_BUTTON_EXIT
- Notimote.HOME_BUTTON_HOME
- Notimote.HOME_BUTTON_BEFORE

## 재생 목록 Text 변경

1. 초기화시 변경 가능

```kotlin
notimote.init {
    ...
    initTextPlaylist{ "재생목록" }
}
```

2. 이외의 변경

먼저 초기화된 Notimote 객체에서 변경.

```kotlin
notimote.setTextPlaylist("재생목록 변경")
```
