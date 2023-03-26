--添加一个值不超过给的上限，乐观锁
local score = redis.call('ZSCORE', KEYS[1], ARGV[1])
if score == nil then
    return nil
end
local total = score + ARGV[2] local score_low_limit = ARGV[3] + 0 local score_high_limit = ARGV[4] + 0
if total <= score_high_limit and total >= score_low_limit then
    return redis.call("ZINCRBY", KEYS[1], ARGV[2], ARGV[1])
end
return nil


