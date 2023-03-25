-- 如果最大的不为空,就给添加分数并返回
-- ZRANGEBYSCORE jj -inf +inf LIMIT 0 1 找到最大的一个 ARGV[1]
local peek = redis.call('ZREVRANGEBYSCORE', KEYS[1], '+inf', '-inf', 'LIMIT', '0', '1')
if #peek ~= 0 then
    redis.call("ZINCRBY", KEYS[1],ARGV[1], peek[1])
    return peek[1]
end
return nil
