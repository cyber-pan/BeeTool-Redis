--添加一个值不超过给的上限，乐观锁
local score = redis.call('ZSCORE', KEYS[1], ARGV[1])
if score == nil then
    return nil
end
local total = score + ARGV[2] local score_limit = ARGV[3] + 0
if total < score_limit then
    local result = redis.call("ZINCRBY", KEYS[1],ARGV[2], ARGV[1])
    return result
end
return nil


