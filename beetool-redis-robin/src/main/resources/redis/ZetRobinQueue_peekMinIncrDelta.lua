-- 如果最大的不为空,就给添加分数并返回
-- ZRANGEBYSCORE jj -inf +inf LIMIT 0 1 找到最小的一个
local peek = redis.call('ZRANGEBYSCORE', KEYS[1], '-inf', '+inf', 'LIMIT', '0', '1')
if #peek ~= 0 then
    redis.call("ZINCRBY", KEYS[1],ARGV[1], peek[1])
    return peek[1]
end
return nil

