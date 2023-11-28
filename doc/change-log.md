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
