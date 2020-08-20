# Notimote 소개

- 리모콘 기능을 알림창에서 쉽게 구현할 수 있도록 도와주는 라이브러리.

# Notimote 사용법

1. Notimote 생성

```kotlin
val notimote: Notimote = Notimote()
```

2. NotificationManager 생성

```kotlin
val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
```

3. NotificationChannel 생성

```kotlin
val name = "Notimote"
val importance = NotificationManager.IMPORTANCE_DEFAULT
val channel = NotificationChannel(NOTIMOTE_CHANNEL.toString(), name, importance)
```

4. Notimote 초기화

```kotlin
notimote.init {
                with { this@MainActivity } // 필수
                receiverClass { NotimoteReceiver::class.java } // 필수
                notificationManager { notificationManager } // 필수
                notificationChannel { channel } // 필수
                initTextPlaylist { "Playlist" }
                setLayoutVisible(
                    arrayOf(Notimote.SOUND, Notimote.CHANNEL, Notimote.PLAYSTOP, Notimote.HOME),
                    View.VISIBLE
                )
            }
```

## setLayoutVisible의 특징

알림창 길이의 한계가 있기 때문에 아래의 보기 중 최대 3가지만 선택하는 것이 좋음.

- Notimote.SOUND
- Notimote.CHANNEL
- Notimote.PLAYSTOP
- Notimote.HOME
