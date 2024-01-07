# 3.4.4 [2023-11-28]
## Improvement
- Introduce `OnlineOfflineNotif`
- Introduce `ServerInfo`
- Introduce `GnssUtils`
- `TermAvAttrsQryResultItem` add `appId`
- Introduce `TermCmdRec`, `UuidRecIdProvider`, etc.
- Introduce `TermAttrsQryResultItem`
- Introduce `ApiClient`, `ApiAuthentication`, etc.
- Introduce `QryAvUploadReq`
- Introduce `ReplyTypes`

## Changed
- Maven group changed to `com.lucendar`
- `WsEventSource2` removed

- Dependencies
  - Bump `lucendar-common` to 2.0.0
  - Bump `jt808-common` to 3.0.0
  - Bump `strm-sdk` to 3.4.4

# 3.4.3
## Improvement
- Merge `gnss-spi` into this module
- Add the following properties to `GnssOpenLiveStrmReq` and `GnssOpenReplayStrmReq`:
  - `trace`
  - `scheme`
  - `dontSendCloseIfInterrupt`
- Add `CommLog` class and `StrmReq` class.

# 3.4.2
## Improvement
- Add android support
- `GnssOpenStrmReq` add `inputAudioCfg` property

## Changed
- Use okhttp to implement websocket
- Remove spring dependencies

# 1.1.0
## Improvement
- Introduce GnssApi, GnssWs

# 1.0.1
## Changed
- remove QryTrkCounterReq
- TermCounter add constructor with fields
